package ru.nsu.fit.asbooster.player.effects

import android.widget.ImageView
import ru.nsu.fit.asbooster.di.ActivityScoped
import ru.nsu.fit.asbooster.player.effects.default.BassBoostEffect
import ru.nsu.fit.asbooster.repository.RequestedImage
import ru.nsu.fit.asbooster.repository.ResourcesImageProvider
import java.lang.IllegalArgumentException
import javax.inject.Inject

@ActivityScoped
class EffectImageProvider @Inject constructor(
    private val imageProvider: ResourcesImageProvider
) {

    fun provideEffectImage(effect: Effect) = when(effect.id) {
        BassBoostEffect.ID -> object : RequestedImage {
            override fun show(view: ImageView) {
                //TODO: get image from image provider
            }

        }
        else -> throw IllegalArgumentException("Unsupported effect")
    }

}