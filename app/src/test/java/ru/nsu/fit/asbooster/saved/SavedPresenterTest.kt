package ru.nsu.fit.asbooster.saved

import com.nhaarman.mockitokotlin2.mock
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import ru.nsu.fit.asbooster.saved.model.TracksRepository
import ru.nsu.fit.asbooster.search.navigation.Router
import ru.nsu.fit.asbooster.view.ViewItemsMapper

class SavedPresenterTest {
    private val savedView: SavedView = mock()
    private val tracksRepository: TracksRepository = mock()
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
}