package ru.nsu.fit.asbooster.saved.model

import javax.inject.Inject
import javax.inject.Singleton

/**
 * Tracks repository implementation to store tracks in memory.
 */
@Singleton
class InMemoryTracksRepository @Inject constructor(): TracksRepository {

    private val tracks = mutableListOf<Track>()

    override fun getTracks() = tracks

    override fun saveTrack(track: Track) {
        tracks.add(track)
    }

    override fun deleteTrack(track: Track) {
        tracks.remove(track)
    }


}