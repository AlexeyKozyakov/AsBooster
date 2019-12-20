package ru.nsu.fit.asbooster.audios.ui

import android.content.Context
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import ru.nsu.fit.asbooster.R
import ru.nsu.fit.asbooster.saved.SavedFragment
import ru.nsu.fit.asbooster.search.SearchFragment
import java.lang.IllegalStateException

private const val FRAGMENT_COUNT = 2
private const val SEARCH_FRAGMENT_POS = 0
private const val SAVED_FRAGMENT_POS = 1

class AudiosPagerAdapter(
    private val context: Context,
    fragmentManager: FragmentManager
) : FragmentPagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    override fun getItem(position: Int) = when(position) {
        SEARCH_FRAGMENT_POS -> SearchFragment()
        SAVED_FRAGMENT_POS -> SavedFragment()
        else -> throw IllegalStateException("Unknown page number")
    }

    override fun getCount() = FRAGMENT_COUNT

    override fun getPageTitle(position: Int) = when(position) {
        SEARCH_FRAGMENT_POS -> context.getString(R.string.search_fragment_title)
        SAVED_FRAGMENT_POS -> context.getString(R.string.saved_fragment_title)
        else -> throw IllegalStateException("Unknown page number")
    }

}