package ru.nsu.fit.asbooster.player

import com.nhaarman.mockitokotlin2.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import org.junit.Test
import ru.nsu.fit.asbooster.formating.NumberFormatter
import ru.nsu.fit.asbooster.player.audio.AudioPlayer
import ru.nsu.fit.asbooster.player.effects.EffectsManager
import ru.nsu.fit.asbooster.repository.AudioRepository
import ru.nsu.fit.asbooster.repository.StringsProvider
import ru.nsu.fit.asbooster.repository.entity.AudioInfo
import ru.nsu.fit.asbooster.saved.model.Track
import ru.nsu.fit.asbooster.saved.model.TracksRepository
import ru.nsu.fit.asbooster.saved.model.entity.EffectInfo
import ru.nsu.fit.asbooster.mappers.ViewItemsMapper

private const val SEEK_PROGRESS = 60
private const val SEEK_FORMATTED = "seek progress"
private const val EFFECT_ID = "id"
private const val EFFECT_FORCE = 10
private const val URL_TO_STREAM = "url to stream"

/**
 * Tests for [PlayerPresenter].
 */
class PlayerPresenterTest{

    private val audioInfo: AudioInfo = mock {
        on { urlToStream } doReturn URL_TO_STREAM
    }
    private val effectInfo: EffectInfo = mock {
        on { id } doReturn EFFECT_ID
        on { force } doReturn EFFECT_FORCE
    }
    private val track: Track = mock {
        on { audioInfo } doReturn audioInfo
        on { effectsInfo } doReturn listOf(effectInfo)
    }
    private val trackViewItem: TrackViewItem = mock()

    private val view: PlayerView = mock()

    private val formatter: NumberFormatter = mock {
        on { formatDuration(eq(SEEK_PROGRESS * 1000)) } doReturn SEEK_FORMATTED
    }
    private val audioPlayer: AudioPlayer = mock {
        on { loaded } doReturn true
    }
    private val uiScope: CoroutineScope = CoroutineScope(Dispatchers.Unconfined)
    private val repository: AudioRepository = mock {
        onBlocking { getStreamUrl(eq(audioInfo.urlToStream!!)) } doReturn URL_TO_STREAM
    }
    private val effectsManager: EffectsManager = mock()
    private val tracksRepository: TracksRepository = mock()
    private val stringsProvider: StringsProvider = mock()
    private val viewItemsMapper: ViewItemsMapper = mock {
        on { audioInfoToTrackViewItem(eq(audioInfo)) } doReturn trackViewItem
    }

    private val playerPresenter = PlayerPresenter(
        view,
        formatter,
        audioPlayer,
        uiScope,
        repository,
        effectsManager,
        tracksRepository,
        stringsProvider,
        viewItemsMapper
    )

    @Test
    fun `set track to player on create`() {
        playerPresenter.onCreate(track)

        verify(view).setTrack(eq(trackViewItem))
        verifyBlocking(audioPlayer) { start(eq(URL_TO_STREAM)) }
    }

    @Test
    fun `release all resources on destroy`() {
        playerPresenter.onDestroy()

        verify(audioPlayer).destroy()
        verify(effectsManager).destroy()
    }

    @Test
    fun `set formatted seek time to view on seek`() {
        playerPresenter.onSeek(SEEK_PROGRESS)

        verify(view).setElapsedTime(eq(SEEK_FORMATTED))
    }

    @Test
    fun `do nothing on play pause click if player is not started`() {
        whenever(audioPlayer.loaded) doReturn false

        playerPresenter.onPlayPauseClick()

        verify(audioPlayer, never()).play()
        verify(audioPlayer, never()).pause()
    }

    @Test
    fun `pause on play pause click if player is playing`() {
        whenever(audioPlayer.playing) doReturn true

        playerPresenter.onPlayPauseClick()

        verify(audioPlayer).pause()
    }

    @Test
    fun `play on play pause click if player is paused`() {
        playerPresenter.onPlayPauseClick()

        verify(audioPlayer).play()
    }

}