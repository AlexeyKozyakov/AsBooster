package ru.nsu.fit.asbooster.audios.di

import android.app.Activity
import dagger.BindsInstance
import dagger.Subcomponent
import ru.nsu.fit.asbooster.audios.AudiosActivityDependencies
import ru.nsu.fit.asbooster.di.ActivityScoped
import ru.nsu.fit.asbooster.saved.di.SavedFragmentComponent
import ru.nsu.fit.asbooster.search.di.SearchFragmentComponent

@Subcomponent(modules = [AudiosModule::class])
@ActivityScoped
interface AudiosActivityComponent {

    fun searchFragmentComponentBuilder(): SearchFragmentComponent.Builder

    fun savedFragmentComponentBuilder(): SavedFragmentComponent.Builder

    fun getAudiosActivityDependencies(): AudiosActivityDependencies

    @Subcomponent.Builder
    interface Builder {

        @BindsInstance
        fun activity(activity: Activity): Builder

        fun build(): AudiosActivityComponent

    }

}