package ru.nsu.fit.asbooster.saved.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import ru.nsu.fit.asbooster.repository.entity.AudioInfo
import ru.nsu.fit.asbooster.saved.model.entity.EffectInfo

/**
 * Base interface for store tracks.
 * Tracks are stored with preserving order.
 * It can be implemented as sql database, in memory database, etc.
 */
interface TracksRepository {

    var saveTrackListener: (Track) -> Unit

    suspend fun isEmpty(): Boolean

    suspend fun getTrack(position: Int): Track

    suspend fun getTracks(): List<Track>

    suspend fun saveTrack(track: Track)

    suspend fun deleteTrack(position: Int)

    suspend fun move(track: Track, insertAfter: Track)
}

@Parcelize
data class Track(
    val audioInfo: AudioInfo,
    val effectsInfo: List<EffectInfo>
) : Parcelable
