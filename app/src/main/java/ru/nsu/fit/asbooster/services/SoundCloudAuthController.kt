package ru.nsu.fit.asbooster.services

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SoundCloudAuthController @Inject constructor() : AuthController {
    //TODO
    override val logged: Boolean
        get() = false

    override fun login(loginInfo: LoginInfo) {
        TODO()
    }

    override fun logout() {
        TODO()
    }
}