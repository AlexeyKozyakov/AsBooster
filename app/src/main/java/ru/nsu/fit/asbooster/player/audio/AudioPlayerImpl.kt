package ru.nsu.fit.asbooster.player.audio

import android.media.MediaPlayer
import kotlinx.coroutines.*
import ru.nsu.fit.asbooster.repository.AudioRepository
import ru.nsu.fit.asbooster.repository.entity.AudioInfo
import javax.inject.Inject
import javax.inject.Singleton

const val TRACKING_DELAY = 1000L

@Singleton
class AudioPlayerImpl @Inject constructor(
    private val background: CoroutineDispatcher,
    private val uiScope: CoroutineScope,
    private val repository: AudioRepository
) : AudioPlayer {

    override var currentAudio: AudioInfo? = null
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
            notifyComplete()
        }
    }

    private val onPrepareCallbacks = mutableListOf<() -> Unit>()

    private var lastSeekCallback: () -> Unit = {}

    override val loaded get() = prepared && !hasError
    override val loading get() = starting

    override val playing get() = mediaPlayer.isPlaying

    override val sessionId get() = mediaPlayer.audioSessionId

    override suspend fun start(audioInfo: AudioInfo) {
        notifyLoadingStart(audioInfo)
        starting = true
        val url  = getStreamUrl(audioInfo) ?: return
        currentAudio = audioInfo
        mediaPlayer.setDataSource(url)
        withContext(background) {
            mediaPlayer.prepare()
        }
        starting = false
        notifyLoadingFinish()
        prepared = true
        onPrepareCallbacks.forEach { it() }
        onPrepareCallbacks.clear()
        lastSeekCallback()
        lastSeekCallback = {}
    }

    override fun play() {
        mediaPlayer.start()
        notifyPlay()
        startProgressTracking()
    }

    private fun startProgressTracking() {
        uiScope.launch {
            while (!destroyed && mediaPlayer.isPlaying) {
                notifyProgress()
                delay(TRACKING_DELAY)
            }
        }
    }

    override fun pause() {
        mediaPlayer.pause()
        notifyPause()
    }

    override fun stop() {
        mediaPlayer.stop()
        notifyStop()
    }

    override fun destroy() {
        destroyed = true
        mediaPlayer.release()
    }

    override fun seekTo(progress: Int) {
        if (prepared) {
            mediaPlayer.seekTo(progress)
        } else {
            lastSeekCallback = {
                mediaPlayer.seekTo(progress)
            }
        }
    }

    override fun attachEffect(id: Int) {
        if (prepared) {
            mediaPlayer.attachAuxEffect(id)
        } else {
            onPrepareCallbacks.add {
                mediaPlayer.attachAuxEffect(id)
            }
        }
    }

    override fun reset() {
        mediaPlayer.reset()
        prepared = false
        hasError = false
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

    private fun notifyProgress() {
        listeners.forEach { it.onProgress(mediaPlayer.currentPosition) }
    }

    private fun notifyPlay() {
        listeners.forEach { it.onPLay() }
    }

    private fun notifyPause() {
        listeners.forEach { it.onPause() }
    }

    private fun notifyStop() {
        listeners.forEach { it.onStop() }
    }

    private fun notifyLoadingStart(audioInfo: AudioInfo) {
        listeners.forEach { it.onLoadingStart(audioInfo) }
    }

    private fun notifyLoadingFinish() {
        listeners.forEach { it.onLoadingFinish() }
    }

    private fun notifyComplete() {
        listeners.forEach { it.onComplete() }
    }

    private fun notifyNewListener(listener: AudioPlayer.Listener) {
        if (!destroyed && mediaPlayer.isPlaying) {
            listener.onProgress(mediaPlayer.currentPosition)
        }
    }
}
