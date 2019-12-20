package ru.nsu.fit.asbooster.audios.ui

import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import ru.nsu.fit.asbooster.saved.SavedFragment
import ru.nsu.fit.asbooster.search.SearchFragment
import java.lang.IllegalStateException

private const val FRAGMENT_COUNT = 2
private const val SAVED_FRAGMENT_POS = 0
private const val SEARCH_FRAGMENT_POS = 1

class AudiosPagerAdapter(
    fragmentManager: FragmentManager
) : FragmentPagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    override fun getItem(position: Int) = when(position) {
        SAVED_FRAGMENT_POS -> SavedFragment()
        SEARCH_FRAGMENT_POS -> SearchFragment()
        else -> throw IllegalStateException("Unknown page number")
    }

    override fun getCount() = FRAGMENT_COUNT

}