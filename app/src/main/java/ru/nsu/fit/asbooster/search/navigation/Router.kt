package ru.nsu.fit.asbooster.search.navigation

import android.app.Activity
import android.content.Intent
import ru.nsu.fit.asbooster.di.ActivityScoped
import ru.nsu.fit.asbooster.player.PlayerActivity
import ru.nsu.fit.asbooster.saved.model.Track
import javax.inject.Inject

const val PLAYER_CODE = 0

const val TRACK_INFO_EXTRA = "track_info"

@ActivityScoped
class Router @Inject constructor(
    private val activity: Activity
) {

    fun openPlayer(track: Track) {
        val intent = Intent(activity, PlayerActivity::class.java)
        intent.putExtra(TRACK_INFO_EXTRA, track)
        activity.startActivityForResult(intent, PLAYER_CODE)
    }

}
