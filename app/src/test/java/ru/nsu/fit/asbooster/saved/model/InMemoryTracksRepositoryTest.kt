package ru.nsu.fit.asbooster.saved.model

import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import org.junit.Assert
import org.junit.Test
import ru.nsu.fit.asbooster.repository.entity.AudioInfo
import ru.nsu.fit.asbooster.saved.model.entity.EffectInfo

class InMemoryTracksRepositoryTest {
    private val audioInfo: AudioInfo = mock()
    private val effectInfo: EffectInfo = mock()
    private val track: Track = mock {
        on { audioInfo } doReturn audioInfo
        on { effectsInfo } doReturn listOf(effectInfo)
    }

    private val repository = InMemoryTracksRepository()

    @Test
    fun `save and get track`() {
        repository.saveTrack(track)
        Assert.assertEquals(track, repository.getTracks().single())
    }

    @Test
    fun `delete and get track`() {
        repository.saveTrack(track)
        repository.deleteTrack(track)
        Assert.assertEquals(0, repository.getTracks().size)
    }
}