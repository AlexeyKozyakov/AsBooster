package ru.nsu.fit.asbooster.audios

import ru.nsu.fit.asbooster.audios.repository.entity.AudioInfo

/**
 * View for displaying VK audios.
 */
interface AudiosView {
    fun showProgress()

    fun hideProgress()

    fun showAudios(audios: List<AudioInfo>)

    fun clearAudios()

}