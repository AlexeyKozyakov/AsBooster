package ru.nsu.fit.asbooster.player.playlist

import ru.nsu.fit.asbooster.saved.model.Track

class LinearPlaylist(
    private val tracks: List<Track>,
    startPos: Int = 0,
    override var looping: Boolean = true
): PlayList {

    var currentPos = startPos
    private set

    val empty = tracks.isEmpty()

    private val size = tracks.size

    override suspend fun hasNext() = looping || currentPos in 0..(tracks.size - 2)

    override suspend fun hasPrevious() = looping || currentPos in 1 until tracks.size

    override suspend fun peekNext(): Track? {
        if (!hasNext()) {
            return null
        }

        return tracks[calculateNextPos()]
    }

    override suspend fun previous(): Track? {
        if (!hasPrevious()) {
            return null
        }

        currentPos = calculatePreviousPos()

        return tracks[currentPos]
    }

    override suspend fun current(): Track? = tracks.elementAtOrNull(currentPos)

    override suspend fun next(): Track? {
        if (!hasNext()) {
            return null
        }

        currentPos = calculateNextPos()

        return tracks[currentPos]
    }

    override fun destroy() = Unit

    private fun calculateNextPos() = if (looping) {
        (size + currentPos + 1) % size
    } else {
        currentPos + 1
    }

    private fun calculatePreviousPos() = if (looping) {
        (size + currentPos - 1) % size
    } else {
        currentPos - 1
    }
}

fun emptyLinearPlaylist() = LinearPlaylist(emptyList())
