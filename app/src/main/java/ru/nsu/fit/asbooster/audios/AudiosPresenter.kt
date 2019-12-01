package ru.nsu.fit.asbooster.audios

import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ru.nsu.fit.asbooster.audios.repository.AudioRepository
import ru.nsu.fit.asbooster.audios.repository.entity.AudioInfo
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
    private val uiScope: CoroutineScope
) {

    private var lastQueryChange = 0L

    fun onQueryChanged(query: String) {
        lastQueryChange = System.currentTimeMillis()
        uiScope.launch {
            delay(TYPE_TIMEOUT)
            if (System.currentTimeMillis() - lastQueryChange > TYPE_TIMEOUT) {
                launchQuery(query)
            }
        }
    }

    fun onAudioClick(info: AudioInfo) {
        //TODO
        Log.d("AS_BOOSTER", "Audio $info clicked")
    }

    private fun launchQuery(query: String) {
        uiScope.launch {
            view.showProgress()
            val audioInfos = audioRepository.searchAudios(query)
            view.hideProgress()
            view.showAudios(audioInfos)
        }
    }

}