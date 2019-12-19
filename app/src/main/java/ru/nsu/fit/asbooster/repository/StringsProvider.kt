package ru.nsu.fit.asbooster.repository

import android.content.Context
import ru.nsu.fit.asbooster.R
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StringsProvider @Inject constructor(
    private val context: Context
) {

    val postDatePrefix get() = context.getString(R.string.posted_text)

    val unknownPostDate get() = context.getString(R.string.unknown_post_date)

    val unknownArtist get() = context.getString(R.string.unknown_artist)

    val bassBoostEffectName get() = context.getString(R.string.bass_boost_effect_name)

    val reverbEffectName get() = context.getString(R.string.reverb_effect_name)
}