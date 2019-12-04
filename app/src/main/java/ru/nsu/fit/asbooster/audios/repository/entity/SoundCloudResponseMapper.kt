package ru.nsu.fit.asbooster.audios.repository.entity

import ru.nsu.fit.asbooster.di.ActivityScoped
import javax.inject.Inject

@ActivityScoped
class SoundCloudResponseMapper @Inject constructor() {
    fun toAudioInfos(soundCloudCollection: SoundCloudAudioCollection)
            = soundCloudCollection.collection?.map {
        AudioInfo(
            it.id ?: -1,
            it.metadata?.artist ?: "",
            it.title ?: "",
            it.imageUrl ?: "",
            it.duration,
            it.playbackCount,
            it.media.transcodings.map { transcoding -> AudioTranscoding(
                transcoding.url,
                transcoding.duration,
                transcoding.format.protocol,
                transcoding.format.mimeType
            ) }
        )
    } ?: listOf()
}