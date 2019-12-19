package ru.nsu.fit.asbooster.player.effects.preloaded

import android.media.audiofx.EnvironmentalReverb
import ru.nsu.fit.asbooster.di.ActivityScoped
import ru.nsu.fit.asbooster.player.audio.AudioPlayer
import javax.inject.Inject

@ActivityScoped
class ReverbEffect @Inject constructor(
    audioPlayer: AudioPlayer
) : Effect {

    private val reverb = EnvironmentalReverb(0, 0).apply {
        enabled = true
        reverbLevel = MIN_LEVEL
    }.also {
        audioPlayer.attachEffect(it.id)
    }

    override val id: String
        get() = ID

    override var force: Int
        get() = levelToForce(reverb.reverbLevel)
        set(value) {
            reverb.reverbLevel = forceToLevel(value)
        }

    override fun destroy() {
        reverb.release()
    }

    companion object {
        const val ID = "reverb"
        const val MIN_LEVEL: Short = -9000
        const val MAX_LEVEL: Short = 2000
    }

    private fun forceToLevel(force: Int) = (MIN_LEVEL + (MAX_LEVEL - MIN_LEVEL) / 100 * force).toShort()

    private fun levelToForce(level: Short) = (level - MIN_LEVEL) * 100 / (MAX_LEVEL - MIN_LEVEL)

}