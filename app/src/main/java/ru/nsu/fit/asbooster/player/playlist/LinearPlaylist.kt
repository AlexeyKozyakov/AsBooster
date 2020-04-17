package ru.nsu.fit.asbooster.player.playlist

import ru.nsu.fit.asbooster.saved.model.Track

class LinearPlaylist(
    private val tracks: List<Track>,
    startPos: Int = 0,
    override var looping: Boolean = true
): PlayList {

    private var currentIndex = startPos

    private val size = tracks.size

    override suspend fun hasNext() = currentIndex < tracks.size

    override suspend fun hasPrevious() = currentIndex > 0

    override suspend fun previous(): Track? {
        if (tracks.isEmpty()) {
            return null
        }

        if (looping) {
            return tracks[(size + (currentIndex--) - 1) % size]
        }

        return if (hasPrevious()) tracks[--currentIndex] else null
    }

    override suspend fun current(): Track = tracks[currentIndex]

    override suspend fun next(): Track? {
        if (tracks.isEmpty()) {
            return null
        }

        if (looping) {
            return tracks[((currentIndex++) + 1) % size]
        }

        return if (hasNext()) tracks[++currentIndex] else null
    }

    override fun destroy() = Unit

}

fun emptyLinearPlaylist() = LinearPlaylist(emptyList())