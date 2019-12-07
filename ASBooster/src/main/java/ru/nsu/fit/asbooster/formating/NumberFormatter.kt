package ru.nsu.fit.asbooster.formating

import android.content.Context
import ru.nsu.fit.asbooster.R
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class NumberFormatter @Inject constructor(
    private val context: Context
) {

    private val format = DecimalFormat("0.#")
    private val inputDateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.US)
    private val outputDateFormat = SimpleDateFormat("dd.MM.yyyy", Locale.US)

    fun formatDuration(timeMillis: Int): String {
        val timeSeconds = timeMillis / 1000
        return "${timeSeconds / 60}:${timeSeconds % 60}"
    }

    fun formatPlaybackCount(count: Int) = when(count) {
        in 0..999 -> count.toString()
        in 1000..999999 -> "${format.format(count / 1000.0)}K"
        else -> "${format.format(count / 1000000.0)}M"
    }

    fun formatPostDate(postDate: String) =
        inputDateFormat.parse(postDate)?.let { date ->
            "${context.getString(R.string.posted_text)} ${outputDateFormat.format(date)}"
        } ?: context.getString(R.string.unknown_post_date)
}