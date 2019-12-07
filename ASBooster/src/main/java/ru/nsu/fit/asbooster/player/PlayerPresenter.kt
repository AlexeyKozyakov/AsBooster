package ru.nsu.fit.asbooster.player

import ru.nsu.fit.asbooster.audios.repository.ImageProvider
import ru.nsu.fit.asbooster.audios.repository.entity.AudioInfo
import ru.nsu.fit.asbooster.formating.NumberFormatter
import ru.nsu.fit.asbooster.di.ActivityScoped
import javax.inject.Inject

@ActivityScoped
class PlayerPresenter @Inject constructor(
    private val view: PlayerView,
    private val imageProvider: ImageProvider,
    private val formatter: NumberFormatter
) {

    private lateinit var audioInfo: AudioInfo

    fun onCreate(audio: AudioInfo) {
        audioInfo = audio
        view.setTrack(toTrackViewItem(audioInfo))
    }

    private fun toTrackViewItem(audioInfo: AudioInfo) = with(audioInfo) {
        TrackViewItem(
            name,
            author,
            imageProvider.provideImage(bigImageUrl, miniImageUrl),
            formatter.formatDuration(duration)
        )
    }

}