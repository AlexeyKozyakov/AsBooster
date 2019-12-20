package ru.nsu.fit.asbooster.di

import android.app.Application
import android.content.Context
import com.squareup.picasso.Picasso
import dagger.Module
import dagger.Provides
import ru.nsu.fit.asbooster.repository.AudioRepository
import ru.nsu.fit.asbooster.repository.SoundCloudAudioRepository
import ru.nsu.fit.asbooster.saved.model.InMemoryTracksRepository
import ru.nsu.fit.asbooster.saved.model.TracksRepository
import javax.inject.Singleton

@Module
class ApplicationModule {

    @Provides
    @Singleton
    fun picasso() = Picasso.get()

    @Provides
    @Singleton
    fun context(application: Application) = application as Context

    @Provides
    @Singleton
    fun repository(repository: SoundCloudAudioRepository) = repository as AudioRepository

    @FragmentScoped
    @Provides
    fun tracksRepository(tracksRepository: InMemoryTracksRepository) = tracksRepository as TracksRepository
}
