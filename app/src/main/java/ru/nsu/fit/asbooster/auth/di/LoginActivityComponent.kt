package ru.nsu.fit.asbooster.auth.di

import android.app.Activity
import dagger.BindsInstance
import dagger.Component
import ru.nsu.fit.asbooster.audios.AudiosPresenter
import ru.nsu.fit.asbooster.auth.LoginPresenter
import ru.nsu.fit.asbooster.di.ActivityScoped

@ActivityScoped
@Component(modules = [LoginActivityModule::class])
interface LoginActivityComponent {

    fun getPresenter(): LoginPresenter

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun activity(activity: Activity): Builder

        fun build(): LoginActivityComponent

    }
}