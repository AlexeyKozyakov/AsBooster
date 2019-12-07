package ru.nsu.fit.asbooster.auth.di

import android.app.Activity
import dagger.BindsInstance
import dagger.Subcomponent
import ru.nsu.fit.asbooster.auth.LoginPresenter
import ru.nsu.fit.asbooster.di.ActivityScoped

@ActivityScoped
@Subcomponent(modules = [LoginActivityModule::class])
interface LoginActivityComponent {

    fun getPresenter(): LoginPresenter

    @Subcomponent.Builder
    interface Builder {

        @BindsInstance
        fun activity(activity: Activity): Builder

        fun build(): LoginActivityComponent

    }
}