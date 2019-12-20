package ru.nsu.fit.asbooster.di

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import ru.nsu.fit.asbooster.audios.di.AudiosActivityComponent
import ru.nsu.fit.asbooster.auth.di.LoginActivityComponent
import ru.nsu.fit.asbooster.player.di.PlayerActivityComponent
import javax.inject.Singleton

@Singleton
@Component(modules = [ApplicationModule::class, CoroutinesModule::class])
interface ApplicationComponent {

    fun audiosNewActivityComponentBuilder(): AudiosActivityComponent.Builder

    fun loginActivityComponentBuilder(): LoginActivityComponent.Builder

    fun playerActivityComponentBuilder(): PlayerActivityComponent.Builder

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): Builder

        fun build(): ApplicationComponent

    }
}