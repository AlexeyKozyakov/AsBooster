package ru.nsu.fit.asbooster.player.di

import android.app.Activity
import dagger.Module
import ru.nsu.fit.asbooster.di.ActivityScoped
import ru.nsu.fit.asbooster.player.PlayerView

@Module
class PlayerModule {

    @ActivityScoped
    fun view(activity: Activity) = activity as PlayerView

}