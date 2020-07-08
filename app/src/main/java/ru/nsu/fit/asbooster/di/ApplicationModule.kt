package ru.nsu.fit.asbooster.di

import android.app.Application
import android.content.Context
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import dagger.Module
import dagger.Provides
import ru.nsu.fit.asbooster.base.lifecicle.ApplicationLifecycle
import ru.nsu.fit.asbooster.base.lifecicle.ApplicationLifecycleImpl
import ru.nsu.fit.asbooster.player.PlaybackController
import ru.nsu.fit.asbooster.player.PlaybackControllerImpl
import ru.nsu.fit.asbooster.player.audio.AudioPlayer
import ru.nsu.fit.asbooster.player.audio.ExoAudioPlayer
import ru.nsu.fit.asbooster.repository.AudioRepository
import ru.nsu.fit.asbooster.repository.SoundCloudAudioRepository
import ru.nsu.fit.asbooster.saved.model.FileTracksRepository
import ru.nsu.fit.asbooster.saved.model.TracksRepository
import javax.inject.Singleton

@Module
class ApplicationModule {

    @Provides
    @Singleton
    fun picasso() = Picasso.get()

    @Provides
    @Singleton
    fun gson() = Gson()

    @Provides
    @Singleton
    fun context(application: Application) = application as Context

    @Provides
    @Singleton
    fun repository(repository: SoundCloudAudioRepository) = repository as AudioRepository

    @Provides
    @Singleton
    fun tracksRepository(tracksRepository: FileTracksRepository) =
        tracksRepository as TracksRepository

    @Provides
    @Singleton
    fun applicationLifecicle(): ApplicationLifecycle = ApplicationLifecycleImpl

    @Singleton
    @Provides
    fun audioPLayer(exoPlayer: ExoAudioPlayer) = exoPlayer as AudioPlayer

    @Singleton
    @Provides
    fun playlistController(playlistControllerImpl: PlaybackControllerImpl) =
        playlistControllerImpl as PlaybackController
}
