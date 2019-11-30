package ru.nsu.fit.asbooster.services.di

import dagger.Module
import dagger.Provides
import ru.nsu.fit.asbooster.services.AuthController
import ru.nsu.fit.asbooster.services.SoundCloudAuthController
import javax.inject.Singleton

@Module
class AuthModule {

    @Provides
    @Singleton
    fun authController(soundCloudAuthController: SoundCloudAuthController)
            = soundCloudAuthController as AuthController

}