package ru.nsu.fit.asbooster.audios.di

import android.app.Activity
import dagger.Module
import dagger.Provides
import ru.nsu.fit.asbooster.audios.AudiosView
import ru.nsu.fit.asbooster.di.ActivityScoped

/**
 * Provides dependencies for [AudiosActivityComponent].
 */
@Module
class AudiosModule {

    @Provides
    @ActivityScoped
    fun view(activity: Activity) = activity as AudiosView

}