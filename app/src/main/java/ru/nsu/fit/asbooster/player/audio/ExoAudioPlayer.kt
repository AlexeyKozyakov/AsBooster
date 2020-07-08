package ru.nsu.fit.asbooster.player.audio

import android.app.Application
import android.net.Uri
import com.google.android.exoplayer2.DefaultLoadControl
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.audio.AuxEffectInfo
import com.google.android.exoplayer2.database.ExoDatabaseProvider
import com.google.android.exoplayer2.source.ConcatenatingMediaSource
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.upstream.DataSpec
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory
import com.google.android.exoplayer2.upstream.ResolvingDataSource
import com.google.android.exoplayer2.upstream.cache.*
import com.google.android.exoplayer2.util.Util
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import ru.nsu.fit.asbooster.base.APPLICATION_NAME
import ru.nsu.fit.asbooster.base.listenable.ListenableImpl
import ru.nsu.fit.asbooster.repository.AudioRepository
import ru.nsu.fit.asbooster.repository.entity.AudioInfo
import javax.inject.Inject
import javax.inject.Singleton


private const val TRACKING_DELAY = 1000L
private const val CACHE_DIR = "exo-cache"
private const val MAX_CACHE_SIZE = 256 * 1024 * 1024
private const val MAX_BUFFER_MS = 360000

@Singleton
class ExoAudioPlayer @Inject constructor(
    private val uiScope: CoroutineScope,
    private val application: Application,
    private val repository: AudioRepository
) : AudioPlayer, PlayListPlayer, ListenableImpl<AudioPlayer.Listener>() {

    private val exoPlayer = SimpleExoPlayer.Builder(application)
        .setLoadControl(
            DefaultLoadControl.Builder()
                .setBufferDurationsMs(
                    DefaultLoadControl.DEFAULT_MIN_BUFFER_MS,
                    MAX_BUFFER_MS,
                    DefaultLoadControl.DEFAULT_BUFFER_FOR_PLAYBACK_MS,
                    DefaultLoadControl.DEFAULT_BUFFER_FOR_PLAYBACK_AFTER_REBUFFER_MS
                )
                .createDefaultLoadControl()
        )
        .build()
        .init()

    private val exoCache = SimpleCache(
        application.cacheDir.resolve(CACHE_DIR),
        LeastRecentlyUsedCacheEvictor(MAX_CACHE_SIZE.toLong()),
        ExoDatabaseProvider(application)
    )

    private var effectId: Int? = null

    private var pendingAudio: AudioInfo? = null

    override val audio: AudioInfo?
        get() = (exoPlayer.currentTag as AudioInfo?) ?: pendingAudio

    override val loaded: Boolean
        get() = exoPlayer.playbackState != ExoPlayer.STATE_IDLE

    override val loading: Boolean
        get() = exoPlayer.isLoading

    override val playing: Boolean
        get() = exoPlayer.isPlaying

    override val sessionId: Int
        get() = exoPlayer.audioSessionId

    override var looping: Boolean
        get() = exoPlayer.repeatMode == ExoPlayer.REPEAT_MODE_ONE
        set(value) {
            val mode = if (value) ExoPlayer.REPEAT_MODE_ONE else ExoPlayer.REPEAT_MODE_ALL
            exoPlayer.repeatMode = mode
            notify { onLoopingModeChanged(value) }
        }

    override suspend fun start(audioInfo: AudioInfo) {
        pendingAudio = audioInfo
        val mediaSource = createMediaSource(audioInfo) ?: return
        exoPlayer.prepare(mediaSource)
        notify { onProgress(0) }
        play()
    }

    override fun start(audioInfos: List<AudioInfo>, startPos: Int) {
        pendingAudio = audioInfos[startPos]
        exoPlayer.prepare(createMediaSource(audioInfos))
        exoPlayer.seekToDefaultPosition(startPos)
        notify { onProgress(0) }
        play()
    }

    override fun play() {
        exoPlayer.playWhenReady = true
        notify { onPlay() }
    }

    override fun pause() {
        exoPlayer.playWhenReady = false
        notify { onPause() }
    }

    override fun destroy() {
        exoPlayer.release()
    }

    override fun trySeekTo(progress: Int): Boolean {
        if (progress > exoPlayer.bufferedPosition) {
            return false
        }
        exoPlayer.seekTo(progress.toLong())
        return true
    }

    override fun attachEffect(id: Int) {
        effectId = id
        exoPlayer.setAuxEffectInfo(AuxEffectInfo(id, 1.0f))
    }

    override fun setAuxEffectLevel(level: Float) {
        effectId?.let {
            exoPlayer.setAuxEffectInfo(AuxEffectInfo(it, level))
        }
    }

    override fun reset() {
        exoPlayer.stop(true)
        notify { onAudioChanged() }
    }

    override fun next(): Boolean {
        if (!exoPlayer.hasNext()) {
            return false
        }
        exoPlayer.next()
        return true
    }

    override fun previous(): Boolean {
        if (!exoPlayer.hasPrevious()) {
            return false
        }
        exoPlayer.previous()
        return true
    }

    private fun createMediaSource(audioInfos: List<AudioInfo>) =
        ConcatenatingMediaSource(
            *audioInfos.mapNotNull { createMediaSource(it) }.toTypedArray()
        )

    private fun createMediaSource(audioInfo: AudioInfo): MediaSource? {
        audioInfo.urlToStream ?: return null

        val defaultDataSourceFactory =
            DefaultHttpDataSourceFactory(Util.getUserAgent(application, APPLICATION_NAME))

        val resolvingDataSourceFactory = ResolvingDataSource.Factory(defaultDataSourceFactory,
            ResolvingDataSource.Resolver { dataSpec ->
                val urlToStream = dataSpec.uri.toString()
                val streamUrl = runBlocking { repository.getStreamUrl(urlToStream) }
                DataSpec(Uri.parse(streamUrl))
            })

        val cacheDataSourceFactory = CacheDataSourceFactory(exoCache, resolvingDataSourceFactory)

        return ProgressiveMediaSource.Factory(cacheDataSourceFactory)
            .setTag(audioInfo)
            .createMediaSource(Uri.parse(audioInfo.urlToStream))
    }

    private fun SimpleExoPlayer.init() = this.apply {
        repeatMode = ExoPlayer.REPEAT_MODE_ALL

        addListener(object : Player.EventListener {
            override fun onPlayerStateChanged(playWhenReady: Boolean, playbackState: Int) {
                if (playbackState == ExoPlayer.STATE_ENDED) {
                    notify { onComplete() }
                }
            }

            override fun onPositionDiscontinuity(reason: Int) {
                when (reason) {
                    Player.DISCONTINUITY_REASON_PERIOD_TRANSITION -> {
                        notify { onComplete() }
                        notify {
                            onAudioChanged()
                            onPlay()
                        }
                    }
                    Player.DISCONTINUITY_REASON_SEEK -> {
                        notify { onAudioChanged() }
                    }
                }
                pendingAudio = null
            }

            override fun onLoadingChanged(isLoading: Boolean) {
                if (isLoading) {
                    notify { onLoadingStart() }
                } else {
                    notify { onLoadingFinish() }
                }
            }

            override fun onIsPlayingChanged(isPlaying: Boolean) {
                if (isPlaying) {
                    startProgressTracking()
                }
            }
        })
    }

    private fun startProgressTracking() {
        uiScope.launch {
            while (exoPlayer.isPlaying) {
                notify {
                    onProgress(exoPlayer.currentPosition.toInt())
                    onBuffered(exoPlayer.bufferedPosition.toInt())
                }
                delay(TRACKING_DELAY)
            }
        }
    }
}
