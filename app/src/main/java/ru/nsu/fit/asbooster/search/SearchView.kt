package ru.nsu.fit.asbooster.search

import ru.nsu.fit.asbooster.search.adapter.AudioItem

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

}