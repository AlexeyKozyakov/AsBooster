package ru.nsu.fit.asbooster.player.effects.preloaded

import android.media.audiofx.LoudnessEnhancer
import ru.nsu.fit.asbooster.di.ActivityScoped
import ru.nsu.fit.asbooster.player.audio.AudioPlayer
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LoudnessEffect @Inject constructor(
    audioPlayer: AudioPlayer
): Effect {

    private val loudnessEnhancer = LoudnessEnhancer(audioPlayer.sessionId).apply {
        setTargetGain(0)
        enabled = true
    }

    override val id: String
        get() = ID

    override var force: Int
        get() = loudnessEnhancer.targetGain.toInt() * 100 / MAX_FORCE
        set(value) {
            loudnessEnhancer.setTargetGain(value * MAX_FORCE / 100)
        }

    override fun destroy() {
        loudnessEnhancer.release()
    }

    companion object {
        const val ID = "loudness"
        const val MAX_FORCE = 20000
    }

}