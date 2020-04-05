package ru.nsu.fit.asbooster.base

import android.app.Activity
import android.view.View
import com.google.android.material.snackbar.Snackbar
import ru.nsu.fit.asbooster.di.ActivityScoped
import javax.inject.Inject

@ActivityScoped
class SnackbarMessageHelper @Inject constructor(
    activity: Activity
) {

    private val contentView: View = activity.findViewById(android.R.id.content)

    fun showMessage(message: String, length: Int = Snackbar.LENGTH_LONG) {
        Snackbar.make(contentView, message, length).show()
    }
}
