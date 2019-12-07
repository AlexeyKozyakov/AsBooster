package ru.nsu.fit.asbooster.audios.repository

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.nsu.fit.asbooster.audios.repository.entity.AudioInfo
import ru.nsu.fit.asbooster.audios.repository.entity.SoundCloudAudioCollection
import ru.nsu.fit.asbooster.audios.repository.entity.SoundCloudResponseMapper
import ru.nsu.fit.asbooster.di.ActivityScoped
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

private const val BASE_SOUND_CLOUD_URL = "https://api-v2.soundcloud.com"

@ActivityScoped
class SoundCloudAudioRepository @Inject constructor(
    private val mapper: SoundCloudResponseMapper
) : AudioRepository {

    private val service = Retrofit.Builder()
        .baseUrl(BASE_SOUND_CLOUD_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(SoundCloudAudioService::class.java)

    override suspend fun searchAudios(query: String) = suspendCoroutine<List<AudioInfo>> {
        service.searchAudios(query).enqueue(object : Callback<SoundCloudAudioCollection> {
            override fun onFailure(call: Call<SoundCloudAudioCollection>, t: Throwable) {
                it.resumeWithException(t)
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

    override fun downloadAudio(audio: AudioInfo) {
        TODO()
    }
}