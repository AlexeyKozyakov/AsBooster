package ru.nsu.fit.asbooster.saved.model

import javax.inject.Inject
import javax.inject.Singleton
import kotlin.collections.LinkedHashSet

/**
 * Tracks repository implementation to store tracks in memory.
 */
@Singleton
class InMemoryTracksRepository @Inject constructor(): TracksRepository {

    override var saveTrackListener: (Track) -> Unit = {}

    private val tracks = LinkedHashSet<Track>()
    private val trackList = mutableListOf<Track>()

    override fun getTrack(position: Int) = trackList[position]

    override fun getTracks() = trackList

    override fun saveTrack(track: Track) {
        if (tracks.add(track)) {
            trackList.add(track)
            saveTrackListener(track)
        }
    }

    override fun deleteTrack(track: Track) {
        if(tracks.remove(track)) {
            trackList.remove(track)
        }
    }

    override fun move(track: Track, insertAfter: Track) {
        val insertIndex = trackList.indexOf(insertAfter)
        if (insertIndex == -1) {
            return
        }
        trackList.remove(track)
        trackList.add(insertIndex, track)
    }

}