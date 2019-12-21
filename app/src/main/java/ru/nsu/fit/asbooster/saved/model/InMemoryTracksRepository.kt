package ru.nsu.fit.asbooster.saved.model

import javax.inject.Inject
import javax.inject.Singleton
import kotlin.collections.LinkedHashSet

/**
 * Tracks repository implementation to store tracks in memory.
 */
@Singleton
class InMemoryTracksRepository @Inject constructor(): TracksRepository {

    private val tracks = LinkedHashSet<Track>()
    private val trackList = mutableListOf<Track>()

    override fun getTracks() = trackList

    override fun saveTrack(track: Track) {
        if (tracks.add(track)) {
            trackList.add(track)
        }
    }

    override fun deleteTrack(track: Track) {
        if(tracks.remove(track)) {
            trackList.remove(track)
        }
    }

    override fun move(from: Track, to: Track) {
        val toIndex = trackList.indexOf(to)
        if (toIndex == -1) {
            return
        }
        trackList.remove(from)
        trackList.add(toIndex, from)
    }

}