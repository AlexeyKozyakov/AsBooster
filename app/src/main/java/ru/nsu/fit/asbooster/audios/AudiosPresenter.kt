package ru.nsu.fit.asbooster.audios

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ru.nsu.fit.asbooster.R
import ru.nsu.fit.asbooster.audios.navigation.AudiosRouter
import ru.nsu.fit.asbooster.repository.AudioRepository
import ru.nsu.fit.asbooster.repository.WebImageProvider
import ru.nsu.fit.asbooster.repository.entity.AudioInfo
import ru.nsu.fit.asbooster.audios.adapter.AudioItem
import ru.nsu.fit.asbooster.formating.NumberFormatter
import ru.nsu.fit.asbooster.di.ActivityScoped
import javax.inject.Inject

/**
 * Presenter for [AudiosView]
 */

private const val TYPE_TIMEOUT = 250L

@ActivityScoped
class AudiosPresenter @Inject constructor(
    private val view: AudiosView,
    private val audioRepository: AudioRepository,
    private val uiScope: CoroutineScope,
    private val router: AudiosRouter,
    private val imageProvider: WebImageProvider,
    private val formatter: NumberFormatter
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
            audioInfos = audioRepository.searchAudios(query) ?: emptyList()
            //TODO: show placeholder if audios is empty
            if (query == lastQuery) {
                view.hideProgress()
                view.showAudios(toViewItems(audioInfos))
            }
        }
    }

    private fun toViewItems(audios: List<AudioInfo>) = audios.map {
        AudioItem(
            it.name,
            it.author,
            imageProvider.provideImage(
                it.smallImageUrl, it.miniImageUrl,
                R.drawable.track_list_item_placeholder_image
            ),
            formatter.formatDuration(it.duration),
            formatter.formatPlaybackCount(it.playbackCount),
            formatter.formatPostDate(it.postDate)
        )
    }
}