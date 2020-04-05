package ru.nsu.fit.asbooster.player.audio

import ru.nsu.fit.asbooster.repository.entity.AudioInfo

interface AudioPlayer {

    val currentAudio: AudioInfo?

    interface Listener {
        fun onPLay() = Unit

        fun onPause() = Unit

        fun onStop() = Unit

        fun onProgress(progress: Int) = Unit

        fun onLoadingStart() = Unit

        fun onLoadingFinish() = Unit

        fun onComplete() = Unit
    }

    val loaded: Boolean

    val loading: Boolean

    val playing: Boolean

    val sessionId: Int

    fun addListener(listener: Listener)

    fun removeListener(listener: Listener)

    suspend fun start(audioInfo: AudioInfo)

    fun play()

    fun pause()

    fun stop()

    fun destroy()

    fun seekTo(progress : Int)

    fun attachEffect(id: Int)

    fun setAuxEffectLevel(level: Float)

    fun reset()

}
