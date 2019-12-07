package ru.nsu.fit.asbooster.auth.di

import android.app.Activity
import dagger.Module
import dagger.Provides
import ru.nsu.fit.asbooster.auth.LoginView
import ru.nsu.fit.asbooster.di.ActivityScoped

@Module
class LoginActivityModule {

    @Provides
    @ActivityScoped
    fun view(activity: Activity) = activity as LoginView

}