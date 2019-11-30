package ru.nsu.fit.asbooster.navigation

import android.app.Activity
import android.content.Intent
import ru.nsu.fit.asbooster.auth.LoginActivity
import ru.nsu.fit.asbooster.di.ActivityScoped
import javax.inject.Inject

@ActivityScoped
class Router @Inject constructor(
    private val activity: Activity
) {

    fun launchLoginActivity() {
        activity.startActivityForResult(Intent(activity, LoginActivity::class.java), LOGIN_CODE)
    }

    fun finishActivity(result: Int) {
        activity.setResult(result)
        activity.finish()
    }

    companion object RequestCodes {
        const val LOGIN_CODE = 0
    }

}