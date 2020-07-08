package ru.nsu.fit.asbooster.base.navigation

import android.app.Activity
import ru.nsu.fit.asbooster.di.ActivityScoped
import ru.nsu.fit.asbooster.player.PlayerActivity
import javax.inject.Inject

@ActivityScoped
class Router @Inject constructor(
    private val activity: Activity
) {
    fun openPlayer() {
        val intent = PlayerActivity.intentToLaunch(activity)
        activity.startActivityForResult(intent, PLAYER_CODE)
    }

    companion object {
        const val PLAYER_CODE = 0
    }
}
