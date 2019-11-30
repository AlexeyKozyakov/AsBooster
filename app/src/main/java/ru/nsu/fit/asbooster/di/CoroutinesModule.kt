package ru.nsu.fit.asbooster.di

import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

@Module
class CoroutinesModule {
    @Provides
    @Singleton
    fun uiScope(): CoroutineScope = CoroutineScope(Dispatchers.Main)

    @Provides
    @Singleton
    fun backgroundDispatcher(): CoroutineDispatcher = Dispatchers.IO
}