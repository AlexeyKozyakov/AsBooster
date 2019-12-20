package ru.nsu.fit.asbooster.saved.model

import ru.nsu.fit.asbooster.repository.entity.AudioInfo
import ru.nsu.fit.asbooster.saved.model.entity.EffectInfo
import javax.inject.Singleton

/**
 * Base interface for store tracks.
 * It can be implemented as sql database, in memory database, etc.
 */
@Singleton
interface SavedTracks {

    fun getTracks(): List<AudioInfo>

    fun saveTrack(track: AudioInfo)

    fun deleteTrack(track: AudioInfo)
}

data class Track(
    val audioInfo: AudioInfo,
    val effectsInfo: List<EffectInfo>
)
