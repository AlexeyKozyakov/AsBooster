package ru.nsu.fit.asbooster.navigation

import android.app.Activity
import android.content.Intent
import ru.nsu.fit.asbooster.auth.LoginActivity
import ru.nsu.fit.asbooster.di.ActivityScoped
import ru.nsu.fit.asbooster.services.AuthController
import javax.inject.Inject

@ActivityScoped
class Router @Inject constructor(
    private val activity: Activity
) {

    fun launchLoginActivity() {
        activity.startActivityForResult(Intent(activity, LoginActivity::class.java), LOGIN_CODE)
    }

    companion object RequestCodes {
        const val LOGIN_CODE = 0
    }

}