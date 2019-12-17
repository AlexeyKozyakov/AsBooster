package ru.nsu.fit.asbooster.player.effects

import ru.nsu.fit.asbooster.repository.RequestedImage

/**
 * Base interface for audio effects.
 */
interface Effect {

    /**
     * Effect name.
     */
    val name: String

    /**
     * Effect image.
     */
    val image: RequestedImage

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