package ru.nsu.fit.asbooster.player.effects

import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.eq
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import org.junit.Assert
import org.junit.Test
import ru.nsu.fit.asbooster.player.effects.preloaded.BassBoostEffect
import ru.nsu.fit.asbooster.player.effects.preloaded.LoudnessEffect
import ru.nsu.fit.asbooster.player.effects.preloaded.ReverbEffect
import ru.nsu.fit.asbooster.saved.model.entity.EffectInfo

private const val EFFECT_FORCE = 42

class EffectsManagerTest {
    private val bassBoostEffect: BassBoostEffect = mock {
        on { id } doReturn "bass"
        on { force } doReturn EFFECT_FORCE
    }
    private val loudnessEffect: LoudnessEffect = mock {
        on { id } doReturn "loudness"
    }
    private val reverbEffect: ReverbEffect = mock {
        on { id } doReturn "reverb"
    }

    private val effectsManager = EffectsManager(
        bassBoostEffect,
        loudnessEffect,
        reverbEffect
    )

    @Test
    fun `list of all effects`() {
        Assert.assertEquals(effectsManager.effects, listOf(bassBoostEffect, loudnessEffect, reverbEffect))
    }

    @Test
    fun `set force test`() {
        effectsManager.setForce(bassBoostEffect.id, EFFECT_FORCE)

        verify(bassBoostEffect).force = eq(EFFECT_FORCE)
    }

    @Test
    fun `get effects settings`() {
        val settings = effectsManager.effectsSettings

        Assert.assertEquals(3, settings.size)
        Assert.assertEquals(EFFECT_FORCE, settings.first().force)
    }

    @Test
    fun `set effects settings`() {
        val effects = listOf(bassBoostEffect).map { EffectInfo(it.id, it.force) }

        effectsManager.effectsSettings = effects

        verify(bassBoostEffect).force = eq(EFFECT_FORCE)
    }
}