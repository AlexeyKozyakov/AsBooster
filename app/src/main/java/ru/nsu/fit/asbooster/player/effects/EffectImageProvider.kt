package ru.nsu.fit.asbooster.player.effects

import ru.nsu.fit.asbooster.R
import ru.nsu.fit.asbooster.di.ActivityScoped
import ru.nsu.fit.asbooster.player.effects.preloaded.BassBoostEffect
import ru.nsu.fit.asbooster.player.effects.preloaded.Effect
import ru.nsu.fit.asbooster.repository.ResourcesImageProvider
import java.lang.IllegalArgumentException
import javax.inject.Inject

@ActivityScoped
class EffectImageProvider @Inject constructor(
    private val imageProvider: ResourcesImageProvider
) {

    fun provideEffectImage(effect: Effect) = when(effect.id) {
        BassBoostEffect.ID -> imageProvider.privideImage(R.drawable.icon_bass_boost)
        else -> throw IllegalArgumentException("Unsupported effect")
    }

}