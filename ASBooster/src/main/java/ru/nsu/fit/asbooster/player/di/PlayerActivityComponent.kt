package ru.nsu.fit.asbooster.player.di

import android.app.Activity
import dagger.BindsInstance
import dagger.Subcomponent
import ru.nsu.fit.asbooster.di.ActivityScoped
import ru.nsu.fit.asbooster.player.PlayerPresenter

@ActivityScoped
@Subcomponent(modules = [PlayerModule::class])
interface PlayerActivityComponent {

    fun getPresenter(): PlayerPresenter

    @Subcomponent.Builder
    interface Builder {

        @BindsInstance
        fun activity(activity: Activity): Builder

        fun build(): PlayerActivityComponent

    }

}