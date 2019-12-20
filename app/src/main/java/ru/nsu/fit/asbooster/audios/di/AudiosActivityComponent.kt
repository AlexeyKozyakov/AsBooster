package ru.nsu.fit.asbooster.audios.di

import android.app.Activity
import dagger.BindsInstance
import dagger.Subcomponent
import ru.nsu.fit.asbooster.di.ActivityScoped
import ru.nsu.fit.asbooster.search.di.SearchFragmentComponent

@Subcomponent
@ActivityScoped
interface AudiosActivityComponent {

    fun searchFragmentComponentBuilder(): SearchFragmentComponent.Builder

    @Subcomponent.Builder
    interface Builder {

        @BindsInstance
        fun activity(activity: Activity): Builder

        fun build(): AudiosActivityComponent

    }

}