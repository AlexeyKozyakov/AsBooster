package ru.nsu.fit.asbooster.audios.di

import androidx.fragment.app.Fragment
import dagger.Module
import dagger.Provides
import ru.nsu.fit.asbooster.audios.AudiosView
import ru.nsu.fit.asbooster.di.FragmentScoped

/**
 * Provides dependencies for [AudiosFragmentComponent].
 */
@Module
class AudiosFragmentModule {

    @Provides
    @FragmentScoped
    fun view(fragment: Fragment) = fragment as AudiosView

}