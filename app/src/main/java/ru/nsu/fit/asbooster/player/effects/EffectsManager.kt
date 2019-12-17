package ru.nsu.fit.asbooster.player.effects

import ru.nsu.fit.asbooster.di.ActivityScoped
import javax.inject.Inject

/**
 * Manages all audio effects.
 */
@ActivityScoped
class EffectsManager @Inject constructor() {


    /**
     * List of all supported effects.
     */
    val effects = listOf<Effect>()

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