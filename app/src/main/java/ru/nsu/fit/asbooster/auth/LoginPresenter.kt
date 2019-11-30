package ru.nsu.fit.asbooster.auth

import android.app.Activity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import ru.nsu.fit.asbooster.R
import ru.nsu.fit.asbooster.di.ActivityScoped
import ru.nsu.fit.asbooster.navigation.Router
import ru.nsu.fit.asbooster.services.AuthController
import javax.inject.Inject

@ActivityScoped
class LoginPresenter @Inject constructor(
    private val view: LoginView,
    private val authController: AuthController,
    private val uiScope: CoroutineScope,
    private val router: Router
) {

    fun login() {
        val loginInfo = view.loginInfo
        if (!loginInfo.valid) {
            view.showMessage(R.string.message_login_empty_fields)
            return
        }
        view.showProgress()
        uiScope.launch {
            val logged = authController.loginAsync(loginInfo).await()
            view.hideProgress()
            if (!logged) {
                view.showMessage(R.string.message_login_fail)
                return@launch
            }
            router.finishActivity(Activity.RESULT_OK)
        }
    }

}
