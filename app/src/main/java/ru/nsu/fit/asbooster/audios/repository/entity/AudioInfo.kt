package ru.nsu.fit.asbooster.audios.repository.entity

import com.google.gson.annotations.SerializedName

data class AudioInfo(
    val id: Long,
    val author: String,
    val name: String,
    val imageUrl: String
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