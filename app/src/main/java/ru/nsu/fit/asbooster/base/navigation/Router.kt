package ru.nsu.fit.asbooster.base.navigation

import android.app.Activity
import android.content.Intent
import ru.nsu.fit.asbooster.di.ActivityScoped
import ru.nsu.fit.asbooster.player.PlayerActivity
import xyz.klinker.android.drag_dismiss.DragDismissIntentBuilder
import javax.inject.Inject

@ActivityScoped
class Router @Inject constructor(
    private val activity: Activity
) {

    fun openPlayer() {
        val intent = Intent(activity, PlayerActivity::class.java)
        DragDismissIntentBuilder(activity)
            .setDragElasticity(DragDismissIntentBuilder.DragElasticity.XXLARGE)
            .setTheme(DragDismissIntentBuilder.Theme.LIGHT)
            .setShowToolbar(false)
            .build(intent)
        activity.startActivityForResult(intent, PLAYER_CODE)
    }

    companion object {
        const val PLAYER_CODE = 0
    }

}
