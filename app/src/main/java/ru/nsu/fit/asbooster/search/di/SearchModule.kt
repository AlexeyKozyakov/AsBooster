package ru.nsu.fit.asbooster.search.di

import androidx.fragment.app.Fragment
import dagger.Module
import dagger.Provides
import ru.nsu.fit.asbooster.di.FragmentScoped
import ru.nsu.fit.asbooster.search.SearchView

@Module
class SearchModule {

    @FragmentScoped
    @Provides
    fun view(fragment: Fragment) = fragment as SearchView

}