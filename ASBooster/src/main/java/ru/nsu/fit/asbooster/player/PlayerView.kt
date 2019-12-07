package ru.nsu.fit.asbooster.player

import ru.nsu.fit.asbooster.audios.repository.RequestedImage

interface PlayerView {

    fun setTrack(track: TrackViewItem)

}

class TrackViewItem(
    val name: String,
    val author: String,
    val cover: RequestedImage,
    val duration: String
)