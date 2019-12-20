package ru.nsu.fit.asbooster.search.di

import android.app.Activity
import dagger.BindsInstance
import dagger.Subcomponent
import ru.nsu.fit.asbooster.search.AudiosPresenter
import ru.nsu.fit.asbooster.di.ActivityScoped

/**
 * Component bounded with SearchFragment.
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