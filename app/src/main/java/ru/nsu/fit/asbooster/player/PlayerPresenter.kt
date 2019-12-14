package ru.nsu.fit.asbooster.player

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import ru.nsu.fit.asbooster.repository.AudioRepository
import ru.nsu.fit.asbooster.repository.ImageProvider
import ru.nsu.fit.asbooster.repository.entity.AudioInfo
import ru.nsu.fit.asbooster.formating.NumberFormatter
import ru.nsu.fit.asbooster.di.ActivityScoped
import ru.nsu.fit.asbooster.player.audio.AudioPlayer
import javax.inject.Inject

@ActivityScoped
class PlayerPresenter @Inject constructor(
    private val view: PlayerView,
    private val imageProvider: ImageProvider,
    private val formatter: NumberFormatter,
    private val audioPlayer: AudioPlayer,
    private val uiScope: CoroutineScope,
    private val repository: AudioRepository
) {

    private lateinit var audioInfo: AudioInfo

    fun onCreate(audio: AudioInfo) {
        audioInfo = audio
        view.setTrack(toTrackViewItem(audioInfo))

        audioInfo.urlToStream?.let {
            uiScope.launch {
                val streamUrl = repository.getStreamUrl(it)
                streamUrl?.let { url ->
                    audioPlayer.start(url)
                    onPlayPauseClick()
                }
            }
        }
    }

    fun onDestroy() {
        audioPlayer.destroy()
    }

    fun onPlayPauseClick() {
        if (!audioPlayer.started) {
            return
        }
        if (audioPlayer.playing) {
            audioPlayer.pause()
            view.showPlayButton()
        } else {
            audioPlayer.play()
            view.showPauseButton()
        }
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