package ru.nsu.fit.asbooster.auth

import kotlinx.coroutines.CoroutineScope
import ru.nsu.fit.asbooster.di.ActivityScoped
import ru.nsu.fit.asbooster.navigation.Router
import javax.inject.Inject

@ActivityScoped
class LoginPresenter @Inject constructor(
    private val view: LoginView,
    private val uiScope: CoroutineScope,
    private val router: Router
) {

    fun login() {

    }

}
