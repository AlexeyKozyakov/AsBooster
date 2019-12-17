package ru.nsu.fit.asbooster.player.effects.preloaded


/**
 * Base interface for audio effects.
 */
interface Effect {

    /**
     * Effect id.
     */
    val id: String

    /**
     * Effect force from 0 to 100, where
     * 0 is disabled,
     * 100 is max force.
     */
    var force: Int

    /**
     * Destroy effect resources.
     */
    fun destroy()

}