package ru.nsu.fit.asbooster.saved.di

import androidx.fragment.app.Fragment
import dagger.BindsInstance
import dagger.Subcomponent
import ru.nsu.fit.asbooster.di.FragmentScoped
import ru.nsu.fit.asbooster.saved.SavedPresenter

@FragmentScoped
@Subcomponent(modules = [SavedModule::class])
interface SavedFragmentComponent {

    fun getPresenter(): SavedPresenter

    @Subcomponent.Builder
    interface Builder {

        @BindsInstance
        fun fragment(fragment: Fragment): Builder

        fun build(): SavedFragmentComponent

    }

}