package ru.nsu.fit.asbooster.audios.repository.entity

import ru.nsu.fit.asbooster.audios.repository.ImageProvider
import ru.nsu.fit.asbooster.di.ActivityScoped
import javax.inject.Inject

@ActivityScoped
class SoundCloudResponseMapper @Inject constructor(
    private val imageProvider: ImageProvider
) {
    fun toAudioInfos(soundCloudCollection: SoundCloudAudioCollection)
            = soundCloudCollection.collection?.map {
        AudioInfo(
            it.id ?: -1,
            it.metadata?.artist ?: "",
            it.title ?: "",
            imageProvider.provideImage(it.imageUrl)
        )
    } ?: listOf()
}