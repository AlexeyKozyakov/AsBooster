package ru.nsu.fit.asbooster.audios.pages

import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_audios.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import ru.nsu.fit.asbooster.audios.AudiosPagerAdapter
import ru.nsu.fit.asbooster.di.ActivityScoped
import ru.nsu.fit.asbooster.saved.model.TracksRepository
import javax.inject.Inject

@ActivityScoped
class AudiosPagerController @Inject constructor(
    activity: AppCompatActivity,
    uiScope: CoroutineScope,
    private val repository: TracksRepository
) {

    private val audiosPager = activity.audios_view_pager

    init {
        audiosPager.adapter = AudiosPagerAdapter(activity, activity.supportFragmentManager)

        uiScope.launch {
            audiosPager.currentItem = getStartPageIndex()
        }
    }

    private suspend fun getStartPageIndex() = if (repository.isEmpty()) {
        AudiosPagerAdapter.SEARCH_FRAGMENT_POS
    } else {
        AudiosPagerAdapter.SAVED_FRAGMENT_POS
    }

}