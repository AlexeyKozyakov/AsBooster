package ru.nsu.fit.asbooster.player.effects

import ru.nsu.fit.asbooster.repository.StringsProvider
import ru.nsu.fit.asbooster.player.effects.preloaded.BassBoostEffect
import ru.nsu.fit.asbooster.player.effects.preloaded.Effect
import ru.nsu.fit.asbooster.player.effects.preloaded.LoudnessEffect
import ru.nsu.fit.asbooster.player.effects.preloaded.ReverbEffect
import java.lang.IllegalArgumentException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class EffectNameProvider @Inject constructor(
    private val stringsProvider: StringsProvider
) {
    fun provideEffectName(effect: Effect) = provideEffectName(effect.id)

    fun provideEffectName(effectId: String) = when(effectId) {
        BassBoostEffect.ID -> stringsProvider.bassBoostEffectName
        ReverbEffect.ID -> stringsProvider.reverbEffectName
        LoudnessEffect.ID -> stringsProvider.loudnessEffectName
        else -> throw IllegalArgumentException("Unsupported effect")
    }
}