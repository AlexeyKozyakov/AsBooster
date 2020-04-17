package ru.nsu.fit.asbooster.saved.model

import javax.inject.Inject
import javax.inject.Singleton
import kotlin.collections.LinkedHashSet

private const val NOT_FOUND = -1

/**
 * Tracks repository implementation to store tracks in memory.
 */
@Singleton
open class InMemoryTracksRepository @Inject constructor(): TracksRepository {
    override suspend fun isEmpty() = tracks.isEmpty()

    override var saveTrackListener: (Track) -> Unit = {}

    private val listeners = mutableListOf<TracksRepository.Listener>()

    private val tracks = LinkedHashSet<Track>()
    private val trackList = mutableListOf<Track>()

    override suspend fun getTrack(position: Int) = trackList[position]

    override suspend fun getTracks() = trackList

    override suspend fun saveTrack(track: Track) {
        if (tracks.add(track)) {
            trackList.add(track)
            saveTrackListener(track)
            notify { onSave() }
            notify { onUpdate() }
        }
    }

    override suspend fun deleteTrack(position: Int) {
        val track = trackList[position]
        if(tracks.remove(track)) {
            trackList.remove(track)
            notify { onUpdate() }
        }
    }

    override suspend fun getPosition(track: Track) = trackList.indexOf(track).takeIf { it != NOT_FOUND }

    override suspend fun move(track: Track, insertAfter: Track) {
        val insertIndex = trackList.indexOf(insertAfter)
        if (insertIndex == -1) {
            return
        }
        trackList.remove(track)
        trackList.add(insertIndex, track)
        notify { onUpdate() }
    }

    override fun addListener(listener: TracksRepository.Listener) {
        listeners.add(listener)
    }

    override fun removeListener(listener: TracksRepository.Listener) {
        listeners.remove(listener)
    }

    /**
     * Only for internal usage.
     * Listener is not called.
     */
    protected fun loadTracks(tracks: List<Track>) {
        this.tracks.addAll(tracks)
        this.trackList.addAll(tracks)
    }

    private fun notify(event: TracksRepository.Listener.() -> Unit) {
        listeners.forEach(event)
    }
}