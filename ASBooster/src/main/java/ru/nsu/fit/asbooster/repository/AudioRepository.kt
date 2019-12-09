package ru.nsu.fit.asbooster.repository

import ru.nsu.fit.asbooster.repository.entity.AudioInfo

interface AudioRepository {

    suspend fun searchAudios(query: String): List<AudioInfo>?

    suspend fun getStreamUrl(url: String): String?
}