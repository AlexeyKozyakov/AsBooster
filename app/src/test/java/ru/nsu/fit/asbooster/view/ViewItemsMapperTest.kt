package ru.nsu.fit.asbooster.view

import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.anyOrNull
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import org.junit.Assert
import org.junit.Test
import ru.nsu.fit.asbooster.formating.NumberFormatter
import ru.nsu.fit.asbooster.player.effects.EffectImageProvider
import ru.nsu.fit.asbooster.player.effects.EffectNameProvider
import ru.nsu.fit.asbooster.repository.WebImageProvider
import ru.nsu.fit.asbooster.repository.entity.AudioInfo
import ru.nsu.fit.asbooster.saved.model.Track

private const val AUDIO_INFO_NAME = "Lift yourself"
private const val AUDIO_INFO_AUTHOR = "Kanye West"
private const val AUDIO_INFO_DURATION = 228
private const val AUDIO_INFO_POST_DATE = "12.12.12"
private const val DURATION_STRING = "duration"
private const val PLAYBACK_COUNT_STRING = "playback count"
private const val POST_DATE_STRING = "post date"

class ViewItemsMapperTest {

    private val audioInfo: AudioInfo = mock {
        on { name } doReturn AUDIO_INFO_NAME
        on { author } doReturn AUDIO_INFO_AUTHOR
        on { duration } doReturn AUDIO_INFO_DURATION
        on { postDate } doReturn AUDIO_INFO_POST_DATE
    }
    private val track: Track = mock {
        on { audioInfo } doReturn audioInfo
    }

    private val webImageProvider: WebImageProvider = mock {
        on { provideImage(anyOrNull(), anyOrNull(), anyOrNull()) } doReturn mock()
    }
    private val formatter: NumberFormatter = mock {
        on { formatDuration(any()) } doReturn DURATION_STRING
        on { formatPlaybackCount(any()) } doReturn PLAYBACK_COUNT_STRING
        on { formatPostDate(any()) } doReturn POST_DATE_STRING
    }
    private val effectNameProvider: EffectNameProvider = mock()
    private val effectImageProvider: EffectImageProvider = mock {
        on { provideEffectImage(any()) } doReturn mock()
    }

    private val viewItemsMapper = ViewItemsMapper(
        webImageProvider,
        formatter,
        effectNameProvider,
        effectImageProvider
    )

    @Test
    fun `audio info to audio items`() {
        val audioItem = viewItemsMapper.audioInfoToAudioItems(listOf(audioInfo)).single()
        Assert.assertEquals(audioInfo.name, audioItem.name)
        Assert.assertEquals(audioInfo.author, audioItem.author)
        Assert.assertEquals(DURATION_STRING, audioItem.duration)
        Assert.assertEquals(PLAYBACK_COUNT_STRING, audioItem.plays)
        Assert.assertEquals(POST_DATE_STRING, audioItem.postDate)
    }

    @Test
    fun `tracks to audio items`() {
        val audioItem = viewItemsMapper.tracksToAudioItems(listOf(track)).single()
        Assert.assertEquals(audioInfo.name, audioItem.name)
        Assert.assertEquals(audioInfo.author, audioItem.author)
        Assert.assertEquals(DURATION_STRING, audioItem.duration)
        Assert.assertEquals(PLAYBACK_COUNT_STRING, audioItem.plays)
        Assert.assertEquals(POST_DATE_STRING, audioItem.postDate)
    }

    @Test
    fun `audio info to track view item`() {
        val trackViewItem = viewItemsMapper.audioInfoToTrackViewItem(audioInfo)
        Assert.assertEquals(audioInfo.name, trackViewItem.name)
        Assert.assertEquals(audioInfo.author, trackViewItem.author)
        Assert.assertEquals(DURATION_STRING, trackViewItem.duration)
    }
}