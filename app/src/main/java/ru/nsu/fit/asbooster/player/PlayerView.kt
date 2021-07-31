package ru.nsu.fit.asbooster.player

import ru.nsu.fit.asbooster.player.effects.ui.EffectItem
import ru.nsu.fit.asbooster.repository.RequestedImage

interface PlayerView {

    fun setTrack(track: TrackViewItem)

    fun showPlayButton()

    fun showPauseButton()

    fun showEffects(effects: List<EffectItem>)

    fun updateProgress(progress: Int)

    fun updateBufferedPosition(position: Int)

    fun setElapsedTime(time:String)

    fun showProgress()

    fun hideProgress()

    fun showLooping(looping: Boolean)

}

class TrackViewItem(
    val name: String,
    val author: String,
    val cover: RequestedImage,
    val duration: String,
    val durationInSeconds: Int
)