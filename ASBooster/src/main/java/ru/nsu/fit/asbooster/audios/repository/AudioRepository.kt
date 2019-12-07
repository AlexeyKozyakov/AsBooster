package ru.nsu.fit.asbooster.audios.repository

import ru.nsu.fit.asbooster.audios.repository.entity.AudioInfo

interface AudioRepository {

    suspend fun searchAudios(query: String): List<AudioInfo>

    fun downloadAudio(audio: AudioInfo)
}