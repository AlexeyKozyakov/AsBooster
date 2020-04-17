package ru.nsu.fit.asbooster.player.preview

import ru.nsu.fit.asbooster.audios.view.AudioItem

interface PlayerPreviewView {

    var listener: Listener

    interface Listener {
        fun onPlayClick() = Unit

        fun onOpenClick() = Unit

        fun onFavoritesClick() = Unit

        fun onCloseClick() = Unit
    }

    fun show(audioItem: AudioItem)

    fun hide()

    fun showPlayButton()

    fun showPauseButton()

    fun showProgress()

    fun hideProgress()

    fun setElapsed(duration: String)
}
