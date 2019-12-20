package ru.nsu.fit.asbooster.player.audio

import android.media.MediaPlayer
import kotlinx.coroutines.*
import ru.nsu.fit.asbooster.di.ActivityScoped
import javax.inject.Inject

const val TRACKING_DELAY = 1000L

@ActivityScoped
class AudioPlayerImpl @Inject constructor(
    private val background: CoroutineDispatcher,
    private val uiScope: CoroutineScope
) : AudioPlayer {

    private var hasError = false
    private var prepared = false
    private var destroyed = false

    override var progressListener: (progress: Int) -> Unit = {}

    private val mediaPlayer = MediaPlayer().apply {
        setOnErrorListener { _, _, _ ->
            hasError = true
            true
        }
    }

    private val onPrepareCallbacks = mutableListOf<() -> Unit>()

    private lateinit var lastSeekCallback: () -> Unit

    override val started get() = prepared && !hasError

    override val playing get() = mediaPlayer.isPlaying

    override val sessionId get() = mediaPlayer.audioSessionId

    override suspend fun start(url: String) {
        mediaPlayer.setDataSource(url)
        withContext(background) {
            mediaPlayer.prepare()
        }
        prepared = true
        onPrepareCallbacks.forEach { it() }
        onPrepareCallbacks.clear()
        lastSeekCallback()
    }

    override fun play() {
        mediaPlayer.start()
        startProgressTracking()
    }

    private fun startProgressTracking() {
        uiScope.launch {
            while (!destroyed && mediaPlayer.isPlaying) {
                progressListener(mediaPlayer.currentPosition)
                delay(TRACKING_DELAY)
            }
        }
    }

    override fun pause() = mediaPlayer.pause()

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

    override fun setAuxEffectLevel(level: Float) {
        mediaPlayer.setAuxEffectSendLevel(level)
    }

}