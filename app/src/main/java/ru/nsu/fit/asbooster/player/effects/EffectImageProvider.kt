package ru.nsu.fit.asbooster.player.effects

import ru.nsu.fit.asbooster.R
import ru.nsu.fit.asbooster.player.effects.preloaded.BassBoostEffect
import ru.nsu.fit.asbooster.player.effects.preloaded.Effect
import ru.nsu.fit.asbooster.player.effects.preloaded.LoudnessEffect
import ru.nsu.fit.asbooster.player.effects.preloaded.ReverbEffect
import ru.nsu.fit.asbooster.repository.ResourcesImageProvider
import java.lang.IllegalArgumentException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class EffectImageProvider @Inject constructor(
    private val imageProvider: ResourcesImageProvider
) {

    fun provideEffectImage(effect: Effect) = provideEffectImage(effect.id)

    fun provideEffectImage(effectId: String) = when(effectId) {
        BassBoostEffect.ID -> imageProvider.provideImage(R.drawable.icon_bass_boost)
        ReverbEffect.ID -> imageProvider.provideImage(R.drawable.reverb_icon)
        LoudnessEffect.ID -> imageProvider.provideImage(R.drawable.loudness_icon)
        else -> throw IllegalArgumentException("Unsupported effect")
    }

}