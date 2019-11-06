package ru.nsu.fit.asbooster.audios

import kotlinx.coroutines.*
import ru.nsu.fit.asbooster.di.FragmentScoped
import javax.inject.Inject

/**
 * Presenter for [AudiosView]
 */
@FragmentScoped
class AudiosPresenter @Inject constructor(
    private val view: AudiosView,
    private val textProvider: HelloTextProvider,
    private val uiScope: CoroutineScope

) {

    /**
     * Example of async request in background with showing progress.
     */
    fun init() = uiScope.launch {
        view.showProgress()
        val text = textProvider.provideTextAsync().await()
        view.hideProgress()
        view.setPlaceholderText(text)
    }

}