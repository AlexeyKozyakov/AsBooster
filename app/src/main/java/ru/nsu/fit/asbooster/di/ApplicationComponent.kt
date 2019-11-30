package ru.nsu.fit.asbooster.di

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import ru.nsu.fit.asbooster.services.di.AuthModule
import javax.inject.Singleton

@Singleton
@Component(modules = [CoroutinesModule::class, AuthModule::class])
interface ApplicationComponent {

    fun mainActivityComponenBuilder(): MainActivityComponent.Builder

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): Builder

        fun build(): ApplicationComponent

    }
}