package ru.nsu.fit.asbooster.player.effects

import javax.inject.Inject

/**
 * Manages all audio effects.
 */
class EffectsManager @Inject constructor() {

    /**
     * Map of all supported effects.
     * This map should contain effect names and references to effects,
     * injected to this class.
     */
    val effects = lazy {
        mapOf<String, Effect>()
    }


    /**
     * Set force of effect by its name if effect exists.
     */
    fun setForce(effectName: String, force: Int) {
        effects.value[effectName]?.force = force
    }

}