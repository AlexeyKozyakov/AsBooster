package ru.nsu.fit.asbooster.player.effects

import ru.nsu.fit.asbooster.di.ActivityScoped
import ru.nsu.fit.asbooster.player.effects.default.BassBoostEffect
import javax.inject.Inject

/**
 * Manages all audio effects.
 */
@ActivityScoped
class EffectsManager @Inject constructor(
    bassBoostEffect: BassBoostEffect
) {


    /**
     * List of all supported effects.
     */
    val effects = listOf<Effect>(
        bassBoostEffect
    )

    /**
     * Set force of effect by its name if effect exists.
     */
    fun setForce(effectName: String, force: Int) {
        effectsMap[effectName]?.force = force
    }


    /**
     * Destroy all effectsMap resources.
     */
    fun destroy() {
        effects.forEach { it.destroy() }
    }

    private val effectsMap = effects.associateBy { it.name }

}