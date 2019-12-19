package ru.nsu.fit.asbooster.player.audio

interface AudioPlayer {

    val started: Boolean

    val playing: Boolean

    val sessionId: Int

    suspend fun start(url: String)

    fun getProgress():Int

    fun play()

    fun pause()

    fun destroy()

    fun seekTo(progress : Int)

    fun attachEffect(id: Int)

    fun setAuxEffectLevel(level: Float)

}
