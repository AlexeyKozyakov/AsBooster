package ru.nsu.fit.asbooster.player.playlist

import ru.nsu.fit.asbooster.saved.model.Track

interface PlayList {

    var looping: Boolean

    suspend fun hasNext(): Boolean

    suspend fun hasPrevious(): Boolean

    /**
     * Return null if there is no previous track.
     */
    suspend fun previous(): Track?


    suspend fun current(): Track?

    /**
     * Return null if there is no next track.
     */
    suspend fun next(): Track?

    fun destroy()
}