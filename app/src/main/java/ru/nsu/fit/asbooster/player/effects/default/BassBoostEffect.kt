package ru.nsu.fit.asbooster.player.effects.default

import android.media.audiofx.BassBoost
import ru.nsu.fit.asbooster.di.ActivityScoped
import ru.nsu.fit.asbooster.player.audio.AudioPlayer
import ru.nsu.fit.asbooster.player.effects.Effect
import javax.inject.Inject

@ActivityScoped
class BassBoostEffect @Inject constructor(
    audioPlayer: AudioPlayer
) : Effect {

    private val bassBoost = BassBoost(0, audioPlayer.sessionId).apply {
        setStrength(0)
        enabled = true
    }

    override val id: String
        get() = ID

    override var force: Int
        get() = bassBoost.roundedStrength.toInt()
        set(value) {
            bassBoost.setStrength(value.toShort())
        }

    override fun destroy() {
        bassBoost.release()
    }

    companion object {
        const val ID = "bass_boost"
    }
}