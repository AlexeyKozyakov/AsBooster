package ru.nsu.fit.asbooster.player.preview

import ru.nsu.fit.asbooster.search.adapter.AudioItem

interface PlayerPreviewView {
    fun show(audioItem: AudioItem)

    fun hide()

    fun showPlayButton()

    fun showPauseButton()

    fun showProgress()

    fun hideProgress()

    fun setElapsed(duration: String)
}
