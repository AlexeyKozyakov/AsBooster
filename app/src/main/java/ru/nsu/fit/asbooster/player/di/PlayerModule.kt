package ru.nsu.fit.asbooster.player.di

import android.app.Activity
import dagger.Module
import dagger.Provides
import ru.nsu.fit.asbooster.di.ActivityScoped
import ru.nsu.fit.asbooster.player.PlayerView
import ru.nsu.fit.asbooster.player.audio.AudioPlayer
import ru.nsu.fit.asbooster.player.audio.AudioPlayerImpl

@Module
class PlayerModule {

    @ActivityScoped
    @Provides
    fun view(activity: Activity) = activity as PlayerView

}