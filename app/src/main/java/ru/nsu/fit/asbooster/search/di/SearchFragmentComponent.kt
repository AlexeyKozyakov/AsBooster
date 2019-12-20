package ru.nsu.fit.asbooster.search.di

import androidx.fragment.app.Fragment
import dagger.BindsInstance
import dagger.Subcomponent
import ru.nsu.fit.asbooster.di.FragmentScoped
import ru.nsu.fit.asbooster.search.SearchPresenter

@FragmentScoped
@Subcomponent(modules = [SearchModule::class])
interface SearchFragmentComponent {

    fun getPresenter(): SearchPresenter

    @Subcomponent.Builder
    interface Builder {

        @BindsInstance
        fun fragment(fragment: Fragment): Builder

        fun build(): SearchFragmentComponent

    }

}