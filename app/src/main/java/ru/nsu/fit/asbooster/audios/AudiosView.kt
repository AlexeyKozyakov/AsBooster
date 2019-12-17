package ru.nsu.fit.asbooster.audios

import ru.nsu.fit.asbooster.audios.adapter.AudioItem

/**
 * View for displaying audios list.
 */
interface AudiosView {
    fun showProgress()

    fun hideProgress()

    fun showAudios(audios: List<AudioItem>)

    fun clearAudios()

}