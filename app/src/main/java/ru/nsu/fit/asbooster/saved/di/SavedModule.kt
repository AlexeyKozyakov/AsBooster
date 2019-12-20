package ru.nsu.fit.asbooster.saved.di

import androidx.fragment.app.Fragment
import dagger.Module
import dagger.Provides
import ru.nsu.fit.asbooster.di.FragmentScoped
import ru.nsu.fit.asbooster.saved.SavedView

@Module
class SavedModule {

    @FragmentScoped
    @Provides
    fun view(fragment: Fragment) = fragment as SavedView
}