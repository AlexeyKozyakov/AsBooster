package ru.nsu.fit.asbooster.audios.di

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import dagger.Module
import dagger.Provides
import ru.nsu.fit.asbooster.di.ActivityScoped
import ru.nsu.fit.asbooster.player.preview.PlayerPreviewView
import ru.nsu.fit.asbooster.player.preview.PlayerPreviewViewImpl

@Module
class AudiosModule {

    @Provides
    @ActivityScoped
    fun playerPreviewView(playerPreviewViewImpl: PlayerPreviewViewImpl) =
        playerPreviewViewImpl as PlayerPreviewView

    @Provides
    @ActivityScoped
    fun appCompatActivity(activity: Activity) = activity as AppCompatActivity

}
