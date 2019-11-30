package ru.nsu.fit.asbooster.services

/**
 * Base interface for authorization on sites: SoundCloud, VK etc.
 */
interface AuthController {

    /**
     * Return true if user has already logged in.
     */
    val logged: Boolean

    fun login(loginInfo: LoginInfo)

    fun logout()

}

class LoginInfo(val login: String, val password: String)