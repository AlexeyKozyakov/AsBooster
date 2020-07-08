package ru.nsu.fit.asbooster.di

import ru.nsu.fit.asbooster.service.AudioServiceStarter
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
@Suppress("unused")
class ApplicationDependencies @Inject constructor(
    audiosServiceStarter: AudioServiceStarter
)
