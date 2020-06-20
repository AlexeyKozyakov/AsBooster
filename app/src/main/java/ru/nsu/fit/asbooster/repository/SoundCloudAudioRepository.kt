package ru.nsu.fit.asbooster.repository

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.nsu.fit.asbooster.repository.entity.AudioInfo
import ru.nsu.fit.asbooster.repository.entity.SoundCloudAudioCollection
import ru.nsu.fit.asbooster.repository.entity.SoundCloudResponseMapper
import ru.nsu.fit.asbooster.repository.entity.SoundCloudUrl
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

private const val BASE_SOUND_CLOUD_URL = "https://api-v2.soundcloud.com"

@Singleton
class SoundCloudAudioRepository @Inject constructor(
    private val mapper: SoundCloudResponseMapper
) : AudioRepository {

    private val service = Retrofit.Builder()
        .baseUrl(BASE_SOUND_CLOUD_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(SoundCloudAudioService::class.java)

    override suspend fun searchAudios(query: String) = suspendCoroutine<List<AudioInfo>?> {
        service.searchAudios(query).enqueue(object : Callback<SoundCloudAudioCollection> {
            override fun onFailure(call: Call<SoundCloudAudioCollection>, t: Throwable) {
                it.resume(null)
            }

            override fun onResponse(
                call: Call<SoundCloudAudioCollection>,
                response: Response<SoundCloudAudioCollection>
            ) {
                response.body()?.let { body ->
                    it.resume(mapper.toAudioInfos(body))
                } ?: it.resume(listOf())
            }
        })
    }

    override suspend fun getStreamUrl(url: String) = suspendCoroutine<String?> {
        service.streamUrl(url).enqueue(object : Callback<SoundCloudUrl> {
            override fun onFailure(call: Call<SoundCloudUrl>, t: Throwable) {
                it.resume(null)
            }

            override fun onResponse(call: Call<SoundCloudUrl>, response: Response<SoundCloudUrl>) {
                it.resume(response.body()?.url)
            }

        })
    }

}
