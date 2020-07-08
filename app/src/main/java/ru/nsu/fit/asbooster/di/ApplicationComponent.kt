package ru.nsu.fit.asbooster.di

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import ru.nsu.fit.asbooster.audios.di.AudiosActivityComponent
import ru.nsu.fit.asbooster.base.navigation.Router
import ru.nsu.fit.asbooster.player.audio.AudioPlayer
import ru.nsu.fit.asbooster.player.di.PlayerActivityComponent
import javax.inject.Singleton

@Singleton
@Component(modules = [ApplicationModule::class, CoroutinesModule::class])
interface ApplicationComponent {

    fun audiosNewActivityComponentBuilder(): AudiosActivityComponent.Builder

    fun playerActivityComponentBuilder(): PlayerActivityComponent.Builder

    fun player(): AudioPlayer

    fun applicationDependencies(): ApplicationDependencies

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): Builder

        fun build(): ApplicationComponent

    }
}