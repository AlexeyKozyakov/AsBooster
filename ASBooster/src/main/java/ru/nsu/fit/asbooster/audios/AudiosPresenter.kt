package ru.nsu.fit.asbooster.audios

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ru.nsu.fit.asbooster.audios.navigation.AudiosRouter
import ru.nsu.fit.asbooster.audios.repository.AudioRepository
import ru.nsu.fit.asbooster.audios.repository.ImageProvider
import ru.nsu.fit.asbooster.audios.repository.entity.AudioInfo
import ru.nsu.fit.asbooster.audios.ui.AudioItem
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
    private val imageProvider: ImageProvider
) {

    private var lastQueryChange = 0L
    private lateinit var audioInfos: List<AudioInfo>

    fun onQueryChanged(query: String) {
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
        view.showProgress()
        uiScope.launch {
            audioInfos = audioRepository.searchAudios(query)
            view.hideProgress()
            view.showAudios(toViewItems(audioInfos))
        }
    }

    private fun toViewItems(audios: List<AudioInfo>) = audios.map {
        AudioItem(it.name, it.author, imageProvider.provideImage(it.imageUrl))
    }
}