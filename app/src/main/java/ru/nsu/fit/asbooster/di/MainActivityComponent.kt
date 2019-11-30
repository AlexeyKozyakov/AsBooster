package ru.nsu.fit.asbooster.di

import android.app.Activity
import dagger.BindsInstance
import dagger.Component
import dagger.Subcomponent
import ru.nsu.fit.asbooster.audios.di.AudiosFragmentComponent

@ActivityScoped
@Subcomponent
interface MainActivityComponent {

    fun audiosFragmentComponentBuilder(): AudiosFragmentComponent.Builder

    @Subcomponent.Builder
    interface Builder {

        @BindsInstance
        fun activity(activity: Activity): Builder

        fun build(): MainActivityComponent

    }

}