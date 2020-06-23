package ru.nsu.fit.asbooster.saved

import ru.nsu.fit.asbooster.audios.view.AudioItem

interface SavedView {

    fun showAudios(audios: MutableList<AudioItem>)

    fun showProgress()

    fun hideProgress()

    fun showPlaceholder()

    fun hidePlaceholder()

    fun removeAudioItem(position: Int)

    fun moveAudioItem(positionFrom: Int, positionTo: Int)

    fun addAudioItem(audio: AudioItem)

    fun showPaused(position: Int)

    fun showPlaying(position: Int)

    fun hideAllInfo(position: Int)

}
