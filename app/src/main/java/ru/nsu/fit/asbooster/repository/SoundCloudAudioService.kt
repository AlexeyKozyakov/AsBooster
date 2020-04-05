package ru.nsu.fit.asbooster.repository

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url
import ru.nsu.fit.asbooster.repository.entity.SoundCloudAudioCollection
import ru.nsu.fit.asbooster.repository.entity.SoundCloudUrl

const val SOUND_CLOUD_CLIENT_ID = "l38jm8md5HpZb10L3ViMpqGy14tIOkaM"
const val TRACKS_LIMIT = 100

interface SoundCloudAudioService {

    @GET("/search/tracks")
    fun searchAudios(
        @Query("q") query: String,
        @Query("limit") limit: Int = TRACKS_LIMIT,
        @Query("client_id") clientId: String = SOUND_CLOUD_CLIENT_ID
    ): Call<SoundCloudAudioCollection>

    @GET
    fun streamUrl(@Url url: String): Call<SoundCloudUrl>

}