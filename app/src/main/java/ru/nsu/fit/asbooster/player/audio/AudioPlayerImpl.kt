package ru.nsu.fit.asbooster.player.audio

import android.media.MediaPlayer
import kotlinx.coroutines.*
import ru.nsu.fit.asbooster.repository.AudioRepository
import ru.nsu.fit.asbooster.repository.entity.AudioInfo
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

const val TRACKING_DELAY = 1000L

@Singleton
class AudioPlayerImpl @Inject constructor(
    private val uiScope: CoroutineScope,
    private val repository: AudioRepository
) : AudioPlayer {

    override var audio: AudioInfo? = null
        private set

    private var hasError = false
    private var prepared = false
    private var destroyed = false
    private var starting = false

    private val listeners = mutableListOf<AudioPlayer.Listener>()

    private val mediaPlayer = MediaPlayer().apply {
        setOnErrorListener { _, _, _ ->
            hasError = true
            true
        }

        setOnCompletionListener {
            notify { onComplete() }
        }
    }

    private val onPrepareCallbacks = mutableListOf<() -> Unit>()

    private var lastSeekCallback: () -> Unit = {}

    override val loaded get() = prepared && !hasError
    override val loading get() = starting

    override val playing get() = mediaPlayer.isPlaying

    override val sessionId get() = mediaPlayer.audioSessionId

    override var looping: Boolean
        get() = mediaPlayer.isLooping
        set(value) {
            mediaPlayer.isLooping = value
            notify { onLoopingModeChanged(value) }
        }

    override suspend fun start(audioInfo: AudioInfo) {
        if (audio == audioInfo) {
            waitPrepare()
            return
        }
        starting = true
        if (audio != null) {
            reset()
        }
        audio = audioInfo
        notify { onLoadingStart(audioInfo) }
        notify { onProgress(0) }
        val url  = getStreamUrl(audioInfo) ?: return
        if (audioInfo !== audio) {
            return
        }
        mediaPlayer.setDataSource(url)
        mediaPlayer.prepareAsync()
        mediaPlayer.setOnPreparedListener {
            starting = false
            prepared =true
            notify { onLoadingFinish() }
            onPrepareCallbacks.forEach { it() }
            onPrepareCallbacks.clear()
            lastSeekCallback()
            lastSeekCallback = {}
            play()
        }
    }

    override fun play() {
        mediaPlayer.start()
        notify { onPLay() }
        startProgressTracking()
    }

    private fun startProgressTracking() {
        uiScope.launch {
            while (!destroyed && mediaPlayer.isPlaying) {
                notify{ onProgress(mediaPlayer.currentPosition) }
                delay(TRACKING_DELAY)
            }
        }
    }

    override fun pause() {
        afterPrepare {
            mediaPlayer.pause()
            notify { onPause() }
        }
    }

    override fun stop() {
        mediaPlayer.stop()
        notify { onStop() }
    }

    override fun destroy() {
        destroyed = true
        mediaPlayer.release()
    }

    override fun seekTo(progress: Int) = afterPrepare { seekTo(progress) }

    override fun attachEffect(id: Int) = afterPrepare { attachAuxEffect(id) }

    override fun reset() {
        mediaPlayer.reset()
        prepared = false
        hasError = false
        audio = null
    }

    override fun setAuxEffectLevel(level: Float) {
        mediaPlayer.setAuxEffectSendLevel(level)
    }

    override fun addListener(listener: AudioPlayer.Listener) {
        listeners.add(listener)

        notifyNewListener(listener)
    }

    override fun removeListener(listener: AudioPlayer.Listener) {
        listeners.remove(listener)
    }

    private suspend fun getStreamUrl(audioInfo: AudioInfo): String? {
        val urlToStream = audioInfo.urlToStream ?: return null
        return repository.getStreamUrl(urlToStream)

    }

    private fun notify(event: AudioPlayer.Listener.() -> Unit) = listeners.forEach(event)

    private fun notifyNewListener(listener: AudioPlayer.Listener) {
        if (destroyed) {
            return
        }

        if (mediaPlayer.isPlaying) {
            listener.onProgress(mediaPlayer.currentPosition)
        }

        if (playing) {
            listener.onPLay()
        }

        if (loading) {
            audio?.let { listener.onLoadingStart(it) }
        }

        listener.onLoopingModeChanged(looping)
    }

    private fun afterPrepare(action: MediaPlayer.() -> Unit) {
        with(mediaPlayer) {
            if (prepared) {
                action()
            } else {
                onPrepareCallbacks.add { action() }
            }
        }
    }

    private suspend fun waitPrepare() {
        suspendCoroutine<Unit> {
            afterPrepare { it.resume(Unit) }
        }
    }
}
