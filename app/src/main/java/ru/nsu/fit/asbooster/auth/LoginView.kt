package ru.nsu.fit.asbooster.auth

import androidx.annotation.StringRes
import ru.nsu.fit.asbooster.services.LoginInfo

interface LoginView {

    val loginInfo: LoginInfo

    fun showMessage(@StringRes stringRes: Int)

    fun showProgress()

    fun hideProgress()

}