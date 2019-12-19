package ru.nsu.fit.asbooster.player.effects

import ru.nsu.fit.asbooster.di.ActivityScoped
import ru.nsu.fit.asbooster.player.effects.preloaded.BassBoostEffect
import ru.nsu.fit.asbooster.player.effects.preloaded.LoudnessEffect
import ru.nsu.fit.asbooster.player.effects.preloaded.ReverbEffect
import javax.inject.Inject


/**
 * Manages all audio effects.
 */
@ActivityScoped
class EffectsManager @Inject constructor(
    bassBoostEffect: BassBoostEffect,
    loudnessEffect: LoudnessEffect,
    reverbEffect: ReverbEffect
) {

    /**
     * List of all supported effects.
     */
    val effects = listOf(
        bassBoostEffect,
        loudnessEffect,
        reverbEffect
    )

    /**
     * Set force of effect by its id if effect exists.
     */
    fun setForce(effectId: String, force: Int) {
        effectsMap[effectId]?.force = force
    }


    /**
     * Destroy all effects resources.
     */
    fun destroy() {
        effects.forEach { it.destroy() }
    }

    private val effectsMap = effects.associateBy { it.id }

}