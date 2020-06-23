package ru.nsu.fit.asbooster.player.audio

import ru.nsu.fit.asbooster.repository.entity.AudioInfo

interface AudioPlayer {

    interface Listener {
        /**
         * Called on start playing.
         */
        fun onPLay() = Unit

        /**
         * Called on pause.
         */
        fun onPause() = Unit

        /**
         * Called when player is stopped.
         */
        fun onStop() = Unit

        /**
         * Called when playing progress is changed.
         */
        fun onProgress(progress: Int) = Unit

        /**
         * Called when track loading is started.
         */
        fun onLoadingStart(audioInfo: AudioInfo) = Unit

        /**
         * Called when track loading is finished.
         */
        fun onLoadingFinish() = Unit

        /**
         * Called when track is played completely.
         */
        fun onComplete() = Unit

        /**
         * Called on looping mode changed
         */
        fun onLoopingModeChanged(looping: Boolean) = Unit
    }

    /**
     * Current playing audio
     */
    val audio: AudioInfo?

    val loaded: Boolean

    val loading: Boolean

    val playing: Boolean

    val sessionId: Int

    var looping: Boolean

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
