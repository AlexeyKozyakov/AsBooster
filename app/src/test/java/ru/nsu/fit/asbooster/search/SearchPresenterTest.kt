package ru.nsu.fit.asbooster.search

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import org.junit.Test
import ru.nsu.fit.asbooster.repository.AudioRepository
import ru.nsu.fit.asbooster.base.navigation.Router
import ru.nsu.fit.asbooster.mappers.ViewItemsMapper

private const val QUERY = "query"

class SearchPresenterTest {
    private val view: SearchView = mock()
    private val audioRepository: AudioRepository = mock()
    private val uiScope: CoroutineScope = CoroutineScope(Dispatchers.Unconfined)
    private val router: Router = mock()
    private val viewItemsMapper: ViewItemsMapper = mock()

    private val searchPresenter = SearchPresenter(
        view,
        audioRepository,
        uiScope,
        router,
        viewItemsMapper
    )

    @Test
    fun `empty audios image on create`() {
        searchPresenter.onCreate()

        verify(view).showEmptyAudiosImage()
    }

    @Test
    fun `on query changed empty query`() {
        searchPresenter.onQueryChanged("")

        verify(view).showEmptyAudiosImage()
    }
}