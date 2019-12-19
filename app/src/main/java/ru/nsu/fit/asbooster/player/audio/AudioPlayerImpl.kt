package ru.nsu.fit.asbooster.player.audio

import android.media.MediaPlayer
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import ru.nsu.fit.asbooster.di.ActivityScoped
import javax.inject.Inject

@ActivityScoped
class AudioPlayerImpl @Inject constructor(
    private val background: CoroutineDispatcher
) : AudioPlayer {

    private var hasError = false
    private var prepared = false

    private val mediaPlayer = MediaPlayer().apply {
        setOnErrorListener { _, _, _ ->
            hasError = true
            true
        }
    }

    private val onPrepareCallbacks = mutableListOf<() -> Unit>()

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
    }

    override fun play() {
        mediaPlayer.start()
    }

    override fun pause() = mediaPlayer.pause()

    override fun destroy() = mediaPlayer.release()

    override fun seekTo(progress: Int) {
        mediaPlayer.seekTo(progress)
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