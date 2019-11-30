package ru.nsu.fit.asbooster.services

import kotlinx.coroutines.Deferred
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SoundCloudAuthController @Inject constructor() : AuthController {

    //TODO
    override val logged: Boolean
        get() = false

    override fun loginAsync(loginInfo: LoginInfo): Deferred<Boolean> {
        TODO()
    }

    override fun logoutAsync(): Deferred<Boolean> {
        TODO()
    }

}