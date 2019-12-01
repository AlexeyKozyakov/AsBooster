package ru.nsu.fit.asbooster.audios.di

import android.app.Activity
import dagger.BindsInstance
import dagger.Subcomponent
import ru.nsu.fit.asbooster.audios.AudiosPresenter
import ru.nsu.fit.asbooster.di.ActivityScoped

/**
 * Component bounded with AudiosFragment.
 */
@Subcomponent(modules = [AudiosModule::class])
@ActivityScoped
interface AudiosActivityComponent {

    fun getPresenter(): AudiosPresenter

    @Subcomponent.Builder
    interface Builder {

        @BindsInstance
        fun activity(activity: Activity): Builder

        fun build(): AudiosActivityComponent

    }

}