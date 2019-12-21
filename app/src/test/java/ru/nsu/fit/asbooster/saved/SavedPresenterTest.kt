package ru.nsu.fit.asbooster.saved

import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import org.junit.Test
import ru.nsu.fit.asbooster.repository.entity.AudioInfo
import ru.nsu.fit.asbooster.saved.model.Track
import ru.nsu.fit.asbooster.saved.model.TracksRepository
import ru.nsu.fit.asbooster.saved.model.entity.EffectInfo
import ru.nsu.fit.asbooster.search.navigation.Router
import ru.nsu.fit.asbooster.view.ViewItemsMapper

class SavedPresenterTest {
    private val savedView: SavedView = mock()

    private val audioInfo: AudioInfo = mock()
    private val effectInfo: EffectInfo = mock()
    private val track: Track = mock {
        on { audioInfo } doReturn audioInfo
        on { effectsInfo } doReturn listOf(effectInfo)
    }

    private val tracksRepository: TracksRepository = mock {
        on { getTracks() } doReturn listOf(track)
    }

    private val viewItemsMapper: ViewItemsMapper = mock()
    private val uiScope: CoroutineScope = CoroutineScope(Dispatchers.Unconfined)
    private val router: Router = mock()

    private val savedPresenter = SavedPresenter(
        savedView,
        tracksRepository,
        viewItemsMapper,
        uiScope,
        router
    )

    @Test
    fun `on create test`() {
        savedPresenter.onCreate()

        verify(savedView).showProgress()
    }

    @Test
    fun `on show test`() {
        savedPresenter.onShow()

        verify(tracksRepository).getTracks()
    }


}