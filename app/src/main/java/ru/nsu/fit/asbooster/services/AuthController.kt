package ru.nsu.fit.asbooster.services

import kotlinx.coroutines.Deferred

/**
 * Base interface for authorization on sites: SoundCloud, VK etc.
 */
interface AuthController {

    /**
     * Return true if user has already logged in.
     */
    val logged: Boolean

    /**
     * Try to login async, return true if login is succeed.
     */
    fun loginAsync(loginInfo: LoginInfo): Deferred<Boolean>

    /**
     * Try to logout async, return tru if logout is succeed.
     */
    fun logoutAsync(): Deferred<Boolean>

}

class LoginInfo(val login: String, val password: String) {
    val valid get() = login.any() && password.any()
}