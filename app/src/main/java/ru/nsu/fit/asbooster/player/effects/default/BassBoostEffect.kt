package ru.nsu.fit.asbooster.player.effects.default

import android.media.audiofx.BassBoost
import android.widget.ImageView
import ru.nsu.fit.asbooster.StringsProvider
import ru.nsu.fit.asbooster.di.ActivityScoped
import ru.nsu.fit.asbooster.player.audio.AudioPlayer
import ru.nsu.fit.asbooster.player.effects.Effect
import ru.nsu.fit.asbooster.repository.RequestedImage
import ru.nsu.fit.asbooster.repository.ResourcesImageProvider
import javax.inject.Inject

@ActivityScoped
class BassBoostEffect @Inject constructor(
    private val stringsProvider: StringsProvider,
    private val imageProvider: ResourcesImageProvider,
    audioPlayer: AudioPlayer
) : Effect {

    private val bassBoost = BassBoost(0, audioPlayer.sessionId).apply {
        setStrength(0)
        enabled = true
    }

    override val name: String
        get() = stringsProvider.bassBoostEffectName

    override val image: RequestedImage
        get() = object : RequestedImage{
            override fun show(view: ImageView) {
                //TODO: Image
            }
        }

    override var force: Int
        get() = bassBoost.roundedStrength.toInt()
        set(value) {
            bassBoost.setStrength(value.toShort())
        }

    override fun destroy() {
        bassBoost.release()
    }
}