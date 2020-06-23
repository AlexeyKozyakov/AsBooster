package ru.nsu.fit.asbooster.repository.entity

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class AudioInfo(
    val id: Long,
    val author: String,
    val name: String,
    val smallImageUrl: String?,
    val bigImageUrl: String?,
    val miniImageUrl: String?,
    val duration: Int,
    val playbackCount: Int,
    val urlToStream: String?,
    val postDate: String
) : Parcelable

@Parcelize
data class AudioTranscoding(
    val url: String,
    val duration: Int,
    val protocol: String,
    val mimeType: String
) : Parcelable

data class SoundCloudAudioCollection(
    val collection: List<SoundCloudAudioInfo>?
)

data class SoundCloudAudioInfo (
    val id: Long?,
    val title: String?,
    @SerializedName("publisher_metadata")
    val metadata: SoundCloudMetadata?,
    @SerializedName("artwork_url")
    val imageUrl: String?,
    @SerializedName("full_duration")
    val duration: Int,
    @SerializedName("playback_count")
    val playbackCount: Int,
    val media: SoundCloudMedia,
    @SerializedName("created_at")
    val postDate: String
)

data class SoundCloudMetadata(
    val artist: String?
)

data class SoundCloudMedia(
    val transcodings: List<SoundCloudTranscoding>
)

data class SoundCloudTranscoding(
    val url: String,
    val duration: Int,
    val format: SoundCloudFormat
)

data class SoundCloudFormat(
    val protocol: String,
    @SerializedName("mime_type")
    val mimeType: String
)

data class SoundCloudUrl(
    val url: String
)
