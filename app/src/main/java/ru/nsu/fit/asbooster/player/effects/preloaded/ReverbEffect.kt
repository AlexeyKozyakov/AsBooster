package ru.nsu.fit.asbooster.player.effects.preloaded

import android.media.audiofx.PresetReverb
import ru.nsu.fit.asbooster.di.ActivityScoped
import ru.nsu.fit.asbooster.player.audio.AudioPlayer
import javax.inject.Inject

@ActivityScoped
class ReverbEffect @Inject constructor(
    private val audioPlayer: AudioPlayer
) : Effect {

    private var reverbForce = 0

    private val reverb = PresetReverb(0, 0).apply {
        preset = PresetReverb.PRESET_LARGEHALL
        enabled = true
    }.also {
        audioPlayer.attachEffect(it.id)
    }

    override val id: String
        get() = ID

    override var force: Int
        get() = reverbForce
        set(value) {
            reverbForce = value
            audioPlayer.setAuxEffectLevel(value / 100.0f)
        }

    override fun destroy() {
        reverb.release()
    }

    companion object {
        const val ID = "reverb"
    }

}