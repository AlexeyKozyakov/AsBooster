package ru.nsu.fit.asbooster.audios

import ru.nsu.fit.asbooster.di.FragmentScoped
import javax.inject.Inject

/**
 * Presenter for [AudiosView]
 */
@FragmentScoped
class AudiosPresenter @Inject constructor(
    private val view: AudiosView,
    private val textProvider: HelloTextProvider
) {

    fun init() {
        view.setPlaceholderText(textProvider.provideText())
    }

}