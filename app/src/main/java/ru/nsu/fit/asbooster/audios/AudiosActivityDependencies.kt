package ru.nsu.fit.asbooster.audios

import ru.nsu.fit.asbooster.di.ActivityScoped
import ru.nsu.fit.asbooster.player.preview.PlayerPreviewPresenter
import javax.inject.Inject

@ActivityScoped
@Suppress("unused")
class AudiosActivityDependencies @Inject constructor(
    private val playerPreviewPresenter: PlayerPreviewPresenter
)
