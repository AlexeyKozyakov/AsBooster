package ru.nsu.fit.asbooster.saved

import ru.nsu.fit.asbooster.search.adapter.AudioItem

interface SavedView {

    fun showAudios(audios: MutableList<AudioItem>)

    fun showProgress()

    fun hideProgress()

    fun showPlaceholder()

    fun hidePlaceholder()

    fun removeTrackItem(position: Int)

}