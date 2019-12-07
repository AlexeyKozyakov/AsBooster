package ru.nsu.fit.asbooster.audios.repository.entity

import android.content.Context
import ru.nsu.fit.asbooster.R
import ru.nsu.fit.asbooster.di.ActivityScoped
import javax.inject.Inject

private const val DEFAULT_IMAGE_CODE = "large"
private const val BIG_IMAGE_CODE = "t500x500"
private const val MINI_IMAGE_CODE = "mini"

@ActivityScoped
class SoundCloudResponseMapper @Inject constructor(
    private val context: Context
) {
    fun toAudioInfos(soundCloudCollection: SoundCloudAudioCollection)
            = soundCloudCollection.collection?.map {
        AudioInfo(
            it.id ?: -1,
            it.metadata?.artist?.takeIf { artist -> artist.any() }
                ?: context.getString(R.string.unknown_artist),
            it.title ?: "",
            it.imageUrl,
            it.imageUrl?.replaceFirst(DEFAULT_IMAGE_CODE, BIG_IMAGE_CODE),
            it.imageUrl?.replaceFirst(DEFAULT_IMAGE_CODE, MINI_IMAGE_CODE),
            it.duration,
            it.playbackCount,
            it.media.transcodings.map { transcoding -> AudioTranscoding(
                transcoding.url,
                transcoding.duration,
                transcoding.format.protocol,
                transcoding.format.mimeType
            ) },
            it.postDate
        )
    } ?: listOf()
}