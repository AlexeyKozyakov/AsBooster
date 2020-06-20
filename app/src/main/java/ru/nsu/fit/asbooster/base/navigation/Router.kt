package ru.nsu.fit.asbooster.base.navigation

import android.app.Activity
import android.content.Intent
import ru.nsu.fit.asbooster.di.ActivityScoped
import ru.nsu.fit.asbooster.player.PlayerActivity
import ru.nsu.fit.asbooster.saved.model.Track
import xyz.klinker.android.drag_dismiss.DragDismissIntentBuilder
import javax.inject.Inject

@ActivityScoped
class Router @Inject constructor(
    private val activity: Activity
) {

    fun openPlayer(track: Track? = null) {
        val intent = Intent(activity, PlayerActivity::class.java)
        DragDismissIntentBuilder(activity)
            .setDragElasticity(DragDismissIntentBuilder.DragElasticity.XXLARGE)
            .setTheme(DragDismissIntentBuilder.Theme.LIGHT)
            .setShowToolbar(false)
            .build(intent)
        intent.putExtra(TRACK_INFO_EXTRA, track)
        activity.startActivityForResult(intent, PLAYER_CODE)
    }

    companion object {
        const val PLAYER_CODE = 0

        const val TRACK_INFO_EXTRA = "track_info"
    }

}
