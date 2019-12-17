package ru.nsu.fit.asbooster.player.audio

interface AudioPlayer {

    val started: Boolean

    val playing: Boolean

    val sessionId: Int

    suspend fun start(url: String)

    fun play()

    fun pause()

    fun destroy()

}