package ru.nsu.fit.asbooster.audios.repository.entity

import com.google.gson.annotations.SerializedName
import ru.nsu.fit.asbooster.audios.repository.RequestedImage

data class AudioInfo(
    val id: Long,
    val author: String,
    val name: String,
    val image: RequestedImage
)

data class SoundCloudAudioCollection(
    val collection: List<SoundCloudAudioInfo>?
)

data class SoundCloudAudioInfo (
    val id: Long?,
    val title: String?,
    @SerializedName("publisher_metadata")
    val metadata: SoundCloudMetadata?,
    @SerializedName("artwork_url")
    val imageUrl: String?
)

data class SoundCloudMetadata(
    val artist: String?
)