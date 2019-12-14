package ru.nsu.fit.asbooster

import android.content.Context
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StringsProvider @Inject constructor(
    private val context: Context
) {

    val postDatePrefix get() = context.getString(R.string.posted_text)

    val unknownPostDate get() = context.getString(R.string.unknown_post_date)

    val unknownArtist get() = context.getString(R.string.unknown_artist)
}