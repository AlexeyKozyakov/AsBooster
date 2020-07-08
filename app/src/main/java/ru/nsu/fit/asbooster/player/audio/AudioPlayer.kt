package ru.nsu.fit.asbooster.player.audio

import ru.nsu.fit.asbooster.base.listenable.Listenable
import ru.nsu.fit.asbooster.repository.entity.AudioInfo

interface AudioPlayer : Listenable<AudioPlayer.Listener> {

    interface Listener {
        /**
         * Called on start playing.
         */
        fun onPlay() = Unit

        /**
         * Called on pause.
         */
        fun onPause() = Unit

        /**
         * Called when playing progress is changed.
         */
        fun onProgress(progress: Int) = Unit

        /**
         * Called when buffered position is changed.
         */
        fun onBuffered(position: Int) = Unit

        /**
         * Called when audio loading is started.
         */
        fun onLoadingStart() = Unit

        /**
         * Called when audio loading is finished.
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

        /**
         * Called when current audio is changed
         */
        fun onAudioChanged() = Unit
    }

    /**
     * Current playing audio
     */
    val audio: AudioInfo?

    val loaded: Boolean

    val loading: Boolean

    val playing: Boolean

    val sessionId: Int

    /**
     * True if player is looping one track
     */
    var looping: Boolean

    suspend fun start(audioInfo: AudioInfo)

    fun play()

    fun pause()

    fun destroy()

    fun trySeekTo(progress : Int): Boolean

    fun attachEffect(id: Int)

    fun setAuxEffectLevel(level: Float)

    fun reset()
}
