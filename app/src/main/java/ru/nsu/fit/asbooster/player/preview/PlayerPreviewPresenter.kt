package ru.nsu.fit.asbooster.player.preview

import dagger.Lazy
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import ru.nsu.fit.asbooster.base.SnackbarMessageHelper
import ru.nsu.fit.asbooster.di.ActivityScoped
import ru.nsu.fit.asbooster.player.audio.AudioPlayer
import ru.nsu.fit.asbooster.base.navigation.Router
import ru.nsu.fit.asbooster.formating.NumberFormatter
import ru.nsu.fit.asbooster.mappers.ViewItemsMapper
import ru.nsu.fit.asbooster.repository.StringsProvider
import ru.nsu.fit.asbooster.repository.entity.AudioInfo
import ru.nsu.fit.asbooster.saved.model.Track
import ru.nsu.fit.asbooster.saved.model.TracksRepository
import javax.inject.Inject

@ActivityScoped
class PlayerPreviewPresenter @Inject constructor(
    private val view: Lazy<PlayerPreviewView>,
    private val player: AudioPlayer,
    private val router: Router,
    private val viewItemsMapper: ViewItemsMapper,
    private val numberFormatter: NumberFormatter,
    private val repository: TracksRepository,
    private val uiScope: CoroutineScope,
    private val messageHelper: SnackbarMessageHelper,
    private val stringsProvider: StringsProvider
) {

    private lateinit var audioInfo: AudioInfo

    private val playerListener = object : AudioPlayer.Listener {
        override fun onPLay() {
            with(view.get()) {
                showPauseButton()
            }
        }

        override fun onPause() {
            view.get().showPlayButton()
        }

        override fun onLoadingStart(audioInfo: AudioInfo) {
            this@PlayerPreviewPresenter.audioInfo = audioInfo
            with(view.get()) {
                val audioItem = viewItemsMapper.trackToAudioItem(Track(audioInfo, emptyList()))
                show(audioItem)
                showProgress()
            }
        }

        override fun onLoadingFinish() {
            view.get().hideProgress()
        }

        override fun onComplete() {
            view.get().showPlayButton()
        }

        override fun onProgress(progress: Int) {
            val elapsedDuration = numberFormatter.formatDuration(progress)
            view.get().setElapsed(elapsedDuration)
        }
    }

    fun onPlayClick() {
        if (player.playing) {
            player.pause()
            view.get().showPlayButton()
        } else {
            player.play()
            view.get().showPauseButton()
        }
    }

    fun onOpenClick() {
        router.openPlayer()
    }

    fun onCloseClick() {
        view.get().hide()
        player.reset()
    }


    fun onAddToFavorites() {
        uiScope.launch {
            messageHelper.showMessage(stringsProvider.savedMessage)
            repository.saveTrack(Track(audioInfo, emptyList()))
        }
    }

    fun onCreate() {
        player.addListener(playerListener)
    }

    fun onDestroy() {
        player.removeListener(playerListener)
    }

}
