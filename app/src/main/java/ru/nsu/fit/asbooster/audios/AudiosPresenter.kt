package ru.nsu.fit.asbooster.audios

import kotlinx.coroutines.*
import ru.nsu.fit.asbooster.di.FragmentScoped
import ru.nsu.fit.asbooster.navigation.Router
import ru.nsu.fit.asbooster.services.AuthController
import javax.inject.Inject

/**
 * Presenter for [AudiosView]
 */
@FragmentScoped
class AudiosPresenter @Inject constructor(
    private val view: AudiosView,
    private val uiScope: CoroutineScope,
    private val authController: AuthController,
    private val router: Router

) {

    fun onCreate() {

    }

    fun login() {
        loginIfNeeded()
    }

    private fun loginIfNeeded() {
        if (!authController.logged) {
            router.launchLoginActivity()
        }
    }

}