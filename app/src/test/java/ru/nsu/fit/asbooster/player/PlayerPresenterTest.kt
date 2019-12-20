package ru.nsu.fit.asbooster.player

import android.media.MediaPlayer
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.eq
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import kotlinx.coroutines.CoroutineScope
import org.junit.Test
import ru.nsu.fit.asbooster.formating.NumberFormatter
import ru.nsu.fit.asbooster.player.audio.AudioPlayer
import ru.nsu.fit.asbooster.player.effects.EffectImageProvider
import ru.nsu.fit.asbooster.player.effects.EffectNameProvider
import ru.nsu.fit.asbooster.player.effects.EffectsManager
import ru.nsu.fit.asbooster.repository.AudioRepository
import ru.nsu.fit.asbooster.repository.StringsProvider
import ru.nsu.fit.asbooster.repository.WebImageProvider
import ru.nsu.fit.asbooster.repository.entity.AudioInfo
import ru.nsu.fit.asbooster.saved.model.Track
import ru.nsu.fit.asbooster.saved.model.TracksRepository
import ru.nsu.fit.asbooster.view.ViewItemsMapper

/**
 * Tests for [PlayerPresenter].
 */

class PlayerPresenterTest{

    private val audioInfo: AudioInfo = mock()
    private val track: Track = mock {
        on { audioInfo } doReturn audioInfo
    }
    private val trackViewItem: TrackViewItem = mock()

    private val view: PlayerView = mock()
    private val formatter: NumberFormatter = mock()
    private val audioPlayer: AudioPlayer = mock()
    private val uiScope: CoroutineScope = mock()
    private val repository: AudioRepository = mock()
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
    }

    @Test
    fun `on destroy`() {

    }

    @Test
    fun `on seek`() {

    }

    @Test
    fun `on play pause click`() {

    }

    @Test
    fun `on effect force changed`() {

    }

    @Test
    fun `init player`() {

    }

    @Test
    fun `init tracker`() {

    }

    @Test
    fun `init effects`() {

    }

    @Test
    fun `to track new item`() {

    }

}