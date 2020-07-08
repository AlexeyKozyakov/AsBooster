package ru.nsu.fit.asbooster.player.effects.preloaded

import android.media.audiofx.BassBoost
import ru.nsu.fit.asbooster.di.ActivityScoped
import ru.nsu.fit.asbooster.player.audio.AudioPlayer
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BassBoostEffect @Inject constructor(
    audioPlayer: AudioPlayer
) : Effect {

    private var currentForce = 0

    private val bassBoost = BassBoost(0, audioPlayer.sessionId).apply {
        setStrength(0)
        enabled = true
    }

    override val id: String
        get() = ID

    override var force: Int
        get() = currentForce
        set(value) {
            currentForce = value
            bassBoost.setStrength((value * STRENGTH_MULTIPLIER).toShort())
        }

    override fun destroy() {
        bassBoost.release()
    }

    companion object {
        const val ID = "bass_boost"
        private const val STRENGTH_MULTIPLIER = 10
    }
}
