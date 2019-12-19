package ru.nsu.fit.asbooster.player.effects

import ru.nsu.fit.asbooster.repository.StringsProvider
import ru.nsu.fit.asbooster.di.ActivityScoped
import ru.nsu.fit.asbooster.player.effects.preloaded.BassBoostEffect
import ru.nsu.fit.asbooster.player.effects.preloaded.Effect
import java.lang.IllegalArgumentException
import javax.inject.Inject

@ActivityScoped
class EffectNameProvider @Inject constructor(
    private val stringsProvider: StringsProvider
) {
    fun provideEffectName(effect: Effect) = when(effect.id) {
        BassBoostEffect.ID -> stringsProvider.bassBoostEffectName
        else -> throw IllegalArgumentException("Unsupported effect")
    }
}