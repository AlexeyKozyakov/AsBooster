package ru.nsu.fit.asbooster.player.preview

interface PlayerPreviewView {
    fun show()

    fun hide()

    fun showPlayButton()

    fun showPauseButton()

    fun showProgress()

    fun hideProgress()
}
