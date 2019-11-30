package ru.nsu.fit.asbooster.di

import android.app.Activity
import dagger.BindsInstance
import dagger.Component
import ru.nsu.fit.asbooster.audios.di.AudiosFragmentComponent

@ActivityScoped
@Component(modules = [MainActivityModule::class])
interface MainActivityComponent {

    fun audiosFragmentComponentBuilder(): AudiosFragmentComponent.Builder

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun activity(activity: Activity): Builder

        fun build(): MainActivityComponent

    }

}