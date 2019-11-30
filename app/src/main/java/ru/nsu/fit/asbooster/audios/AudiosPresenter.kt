package ru.nsu.fit.asbooster.audios

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
    private val router: Router,
    private val authController: AuthController

) {

    fun onCreate() {
        if (!authController.logged) {
            view.showLoginButton()
        }
    }

    fun login() {
        router.launchLoginActivity()
    }

}