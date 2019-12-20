package ru.nsu.fit.asbooster.saved.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue
import ru.nsu.fit.asbooster.repository.entity.AudioInfo
import ru.nsu.fit.asbooster.saved.model.entity.EffectInfo

/**
 * Base interface for store tracks.
 * It can be implemented as sql database, in memory database, etc.
 */
interface TracksRepository {

    fun getTracks(): List<Track>

    fun saveTrack(track: Track)

    fun deleteTrack(track: Track)
}

@Parcelize
data class Track(
    val audioInfo: AudioInfo,
    val effectsInfo: List<EffectInfo>
) : Parcelable
