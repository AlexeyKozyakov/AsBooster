package ru.nsu.fit.asbooster.formating

import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import org.junit.Assert
import org.junit.Test
import ru.nsu.fit.asbooster.repository.StringsProvider

private const val DURATION_RAW = 73000

private const val DURATION_FORMATTED = "1:13"
private const val PLAYBACK_RAW = 1312

private const val PLAYBACK_FORMATTED = "1,3K"

private const val POST_DATE_PREFIX = "Posted"
private const val POST_DATE_RAW = "2019-08-12T12:31:48Z"
private const val POST_DATE_FORMATTED = "$POST_DATE_PREFIX 12.08.2019"

private const val UNKNOWN_POST_DATE = "Unknown post date"

/**
 * Tests for [NumberFormatter].
 */
class NumberFormatterTest {

    private val stringsProvider: StringsProvider = mock {
        on { postDatePrefix } doReturn POST_DATE_PREFIX
        on { unknownPostDate } doReturn UNKNOWN_POST_DATE
    }
    private val numberFormatter = NumberFormatter(stringsProvider)

    @Test
    fun `format track duration`() {
        val duration = numberFormatter.formatDuration(DURATION_RAW)
        Assert.assertEquals(DURATION_FORMATTED, duration)
    }

    @Test
    fun `format playback count`() {
        val playbackCount = numberFormatter.formatPlaybackCount(PLAYBACK_RAW)
        Assert.assertEquals(PLAYBACK_FORMATTED, playbackCount)
    }

    @Test
    fun `format post date`() {
        val postDate = numberFormatter.formatPostDate(POST_DATE_RAW)
        Assert.assertEquals(POST_DATE_FORMATTED, postDate)
    }

    @Test
    fun `format unknown post date`() {
        val postDate = numberFormatter.formatPostDate("")
        Assert.assertEquals(UNKNOWN_POST_DATE, postDate)
    }

}