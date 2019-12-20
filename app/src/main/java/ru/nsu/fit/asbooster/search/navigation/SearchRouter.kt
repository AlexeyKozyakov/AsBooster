package ru.nsu.fit.asbooster.search.navigation

import android.app.Activity
import android.content.Intent
import ru.nsu.fit.asbooster.repository.entity.AudioInfo
import ru.nsu.fit.asbooster.di.ActivityScoped
import ru.nsu.fit.asbooster.di.FragmentScoped
import ru.nsu.fit.asbooster.player.PlayerActivity
import javax.inject.Inject

const val PLAYER_CODE = 0

const val AUDIO_INFO_EXTRA = "audio_info"

@ActivityScoped
class SearchRouter @Inject constructor(
    private val activity: Activity
) {

    fun openPlayer(audio: AudioInfo) {
        val intent = Intent(activity, PlayerActivity::class.java)
        intent.putExtra(AUDIO_INFO_EXTRA, audio)
        activity.startActivityForResult(intent, PLAYER_CODE)
    }

}