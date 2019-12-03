package ru.nsu.fit.asbooster.audios.navigation

import android.app.Activity
import android.content.Intent
import ru.nsu.fit.asbooster.audios.repository.entity.AudioInfo
import ru.nsu.fit.asbooster.di.ActivityScoped
import ru.nsu.fit.asbooster.player.PlayerActivity
import javax.inject.Inject

const val PLAYER_CODE = 0

const val AUDIO_INFO_EXTRA = "audio_info"

@ActivityScoped
class AudiosRouter @Inject constructor(
    private val activity: Activity
) {

    fun openPlayer(audio: AudioInfo) {
        val intent = Intent(activity, PlayerActivity::class.java)
        //TODO: put audio data to intent
        activity.startActivityForResult(intent, PLAYER_CODE)
    }

}