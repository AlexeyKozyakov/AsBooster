package ru.nsu.fit.asbooster.saved.model

import javax.inject.Inject
import javax.inject.Singleton
import kotlin.collections.LinkedHashSet

/**
 * Tracks repository implementation to store tracks in memory.
 */
@Singleton
open class InMemoryTracksRepository @Inject constructor(): TracksRepository {

    override suspend fun isEmpty() = tracks.isEmpty()

    override var saveTrackListener: (Track) -> Unit = {}

    private val tracks = LinkedHashSet<Track>()
    private val trackList = mutableListOf<Track>()

    override suspend fun getTrack(position: Int) = trackList[position]

    override suspend fun getTracks() = trackList

    override suspend fun saveTrack(track: Track) {
        if (tracks.add(track)) {
            trackList.add(track)
            saveTrackListener(track)
        }
    }

    override suspend fun deleteTrack(position: Int) {
        val track = trackList[position]
        if(tracks.remove(track)) {
            trackList.remove(track)
        }
    }

    override suspend fun move(track: Track, insertAfter: Track) {
        val insertIndex = trackList.indexOf(insertAfter)
        if (insertIndex == -1) {
            return
        }
        trackList.remove(track)
        trackList.add(insertIndex, track)
    }

    /**
     * Only for internal usage.
     * Save listener is not called.
     */
    protected fun saveTracks(tracks: List<Track>) {
        this.tracks.addAll(tracks)
        this.trackList.addAll(tracks)
    }
}