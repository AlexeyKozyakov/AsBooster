package ru.nsu.fit.asbooster.di

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import ru.nsu.fit.asbooster.audios.di.AudiosActivityComponent
import ru.nsu.fit.asbooster.auth.di.LoginActivityComponent
import javax.inject.Singleton

@Singleton
@Component(modules = [CoroutinesModule::class])
interface ApplicationComponent {

    fun audiosActivityComponentBuilder(): AudiosActivityComponent.Builder

    fun loginActivityComponentBuilder(): LoginActivityComponent.Builder

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): Builder

        fun build(): ApplicationComponent

    }
}