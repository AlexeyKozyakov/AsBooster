package ru.nsu.fit.asbooster.search

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ru.nsu.fit.asbooster.R
import ru.nsu.fit.asbooster.search.navigation.SearchRouter
import ru.nsu.fit.asbooster.repository.AudioRepository
import ru.nsu.fit.asbooster.repository.WebImageProvider
import ru.nsu.fit.asbooster.repository.entity.AudioInfo
import ru.nsu.fit.asbooster.search.adapter.AudioItem
import ru.nsu.fit.asbooster.formating.NumberFormatter
import ru.nsu.fit.asbooster.di.FragmentScoped
import ru.nsu.fit.asbooster.view.ViewItemsMapper
import javax.inject.Inject

/**
 * Presenter for [SearchView]
 */

private const val TYPE_TIMEOUT = 250L

@FragmentScoped
class SearchPresenter @Inject constructor(
    private val view: SearchView,
    private val audioRepository: AudioRepository,
    private val uiScope: CoroutineScope,
    private val router: SearchRouter,
    private val viewItemsMapper: ViewItemsMapper
) {

    private var lastQueryChange = 0L
    private lateinit var audioInfos: List<AudioInfo>
    private lateinit var lastQuery: String

    fun onCreate() {
        view.showEmptyAudiosImage()
    }

    fun onQueryChanged(query: String) {
        lastQuery = query
        if (query.isEmpty()) {
            view.showEmptyAudiosImage()
            return
        }
        lastQueryChange = System.currentTimeMillis()
        uiScope.launch {
            delay(TYPE_TIMEOUT)
            if (System.currentTimeMillis() - lastQueryChange > TYPE_TIMEOUT) {
                launchQuery(query)
            }
        }
    }

    fun onAudioClick(index: Int) {
        router.openPlayer(audioInfos[index])
    }

    private fun launchQuery(query: String) {
        if (query != lastQuery) {
            return
        }
        view.showProgress()
        uiScope.launch {
            val requestedAudios = audioRepository.searchAudios(query) ?: emptyList()
            //TODO: show placeholder if audios is empty
            if (query == lastQuery) {
                audioInfos = requestedAudios
                view.hideProgress()
                view.showAudios(viewItemsMapper.audioInfoToAudioItems(audioInfos))
            }
        }
    }
}