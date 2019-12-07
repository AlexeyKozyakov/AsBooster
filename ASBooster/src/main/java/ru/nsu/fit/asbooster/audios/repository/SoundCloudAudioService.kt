package ru.nsu.fit.asbooster.audios.repository

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import ru.nsu.fit.asbooster.audios.repository.entity.SoundCloudAudioCollection

private const val SOUND_CLOUD_CLIENT_ID = "1XduoqV99lROqCMpijtDo5WnJmpaLuYm"
private const val TRACKS_LIMIT = 100

interface SoundCloudAudioService {

    @GET("/search/tracks")
    fun searchAudios(
        @Query("q") query: String,
        @Query("limit") limit: Int = TRACKS_LIMIT,
        @Query("client_id") clientId: String = SOUND_CLOUD_CLIENT_ID
    ): Call<SoundCloudAudioCollection>

}