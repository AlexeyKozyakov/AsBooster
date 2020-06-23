package ru.nsu.fit.asbooster.audios.pages

import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_audios.*
import ru.nsu.fit.asbooster.audios.AudiosPagerAdapter
import ru.nsu.fit.asbooster.di.ActivityScoped
import ru.nsu.fit.asbooster.saved.model.TracksRepository
import javax.inject.Inject

@ActivityScoped
class AudiosPagerController @Inject constructor(
    activity: AppCompatActivity,
    private val repository: TracksRepository
) {

    private val audiosPager = activity.audios_view_pager

    init {
        audiosPager.adapter = AudiosPagerAdapter(activity, activity.supportFragmentManager)
        chooseStartPage()
    }

    private fun chooseStartPage() {
        val startPageIndex =
            if (repository.isEmpty()) AudiosPagerAdapter.SEARCH_FRAGMENT_POS else AudiosPagerAdapter.SAVED_FRAGMENT_POS
        audiosPager.setCurrentItem(startPageIndex, false)
    }
}
