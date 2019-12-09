package ru.nsu.fit.asbooster.repository.entity

import android.content.Context
import ru.nsu.fit.asbooster.R
import ru.nsu.fit.asbooster.repository.SOUND_CLOUD_CLIENT_ID
import javax.inject.Inject
import javax.inject.Singleton

private const val DEFAULT_IMAGE_CODE = "large"
private const val BIG_IMAGE_CODE = "t500x500"
private const val MINI_IMAGE_CODE = "mini"
private const val STREAMING_PROTOCOL = "progressive"
private const val CLIENT_ID_PARAM = "client_id"

@Singleton
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
            it.media.transcodings
                .find { it.format.protocol == STREAMING_PROTOCOL }?.url
                    + "?$CLIENT_ID_PARAM=$SOUND_CLOUD_CLIENT_ID",
            it.postDate
        )
    } ?: listOf()
}