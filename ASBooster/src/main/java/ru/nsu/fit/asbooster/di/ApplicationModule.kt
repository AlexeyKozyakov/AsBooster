package ru.nsu.fit.asbooster.di

import android.app.Application
import android.content.Context
import com.squareup.picasso.Picasso
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ApplicationModule {

    @Provides
    @Singleton
    fun picasso() = Picasso.get()

    @Provides
    @Singleton
    fun context(application: Application) = application as Context
}