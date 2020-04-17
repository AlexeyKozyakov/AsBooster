package ru.nsu.fit.asbooster.search

import ru.nsu.fit.asbooster.audios.view.AudioItem

/**
 * View for displaying audios list.
 */
interface SearchView {

    fun showProgress()

    fun hideProgress()

    fun showAudios(audios: MutableList<AudioItem>)

    fun clearAudios()

    fun showEmptyAudiosImage()

    fun hideEmptyAudiosImage()

    fun showPaused(position: Int)

    fun showPlaying(position: Int)

    fun hideAllInfo(position: Int)
}