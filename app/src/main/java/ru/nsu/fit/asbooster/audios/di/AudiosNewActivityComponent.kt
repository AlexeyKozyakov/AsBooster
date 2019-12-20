package ru.nsu.fit.asbooster.audios.di

import android.app.Activity
import dagger.BindsInstance
import dagger.Subcomponent
import ru.nsu.fit.asbooster.di.ActivityScoped

@Subcomponent
@ActivityScoped
interface AudiosNewActivityComponent {

    @Subcomponent.Builder
    interface Builder {

        @BindsInstance
        fun activity(activity: Activity): Builder

        fun build(): AudiosNewActivityComponent

    }

}