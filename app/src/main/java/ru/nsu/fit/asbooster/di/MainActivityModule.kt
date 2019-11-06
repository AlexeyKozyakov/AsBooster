package ru.nsu.fit.asbooster.di

import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

@Module
class MainActivityModule {

    @Provides
    @ActivityScoped
    fun uiScope(): CoroutineScope = CoroutineScope(Dispatchers.Main)

    @Provides
    @ActivityScoped
    fun backgroundDispatcher(): CoroutineDispatcher = Dispatchers.IO

}