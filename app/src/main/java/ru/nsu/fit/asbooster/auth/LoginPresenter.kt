package ru.nsu.fit.asbooster.auth

import kotlinx.coroutines.CoroutineScope
import ru.nsu.fit.asbooster.di.ActivityScoped
import ru.nsu.fit.asbooster.search.navigation.SearchRouter
import javax.inject.Inject

@ActivityScoped
class LoginPresenter @Inject constructor(
    private val view: LoginView,
    private val uiScope: CoroutineScope,
    private val router: SearchRouter
) {

    fun login() {

    }

}
