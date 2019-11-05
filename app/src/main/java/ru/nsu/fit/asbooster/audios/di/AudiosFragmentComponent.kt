package ru.nsu.fit.asbooster.audios.di

import androidx.fragment.app.Fragment
import dagger.BindsInstance
import dagger.Subcomponent
import ru.nsu.fit.asbooster.audios.AudiosPresenter
import ru.nsu.fit.asbooster.di.FragmentScoped

/**
 * Component bounded with AudiosFragment.
 */
@Subcomponent(modules = [AudiosFragmentModule::class])
@FragmentScoped
interface AudiosFragmentComponent {

    fun getPresenter(): AudiosPresenter

    @Subcomponent.Builder
    interface Builder {

        @BindsInstance
        fun fragment(fragment: Fragment): Builder

        fun build(): AudiosFragmentComponent

    }

}