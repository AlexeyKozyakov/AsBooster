package ru.nsu.fit.asbooster.player.preview

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import ru.nsu.fit.asbooster.base.SnackbarMessageHelper
import ru.nsu.fit.asbooster.di.ActivityScoped
import ru.nsu.fit.asbooster.player.audio.AudioPlayer
import ru.nsu.fit.asbooster.base.navigation.Router
import ru.nsu.fit.asbooster.formating.NumberFormatter
import ru.nsu.fit.asbooster.mappers.ViewItemsMapper
import ru.nsu.fit.asbooster.player.PlaybackControllerImpl
import ru.nsu.fit.asbooster.player.effects.EffectsManager
import ru.nsu.fit.asbooster.repository.StringsProvider
import ru.nsu.fit.asbooster.repository.entity.AudioInfo
import ru.nsu.fit.asbooster.saved.model.Track
import ru.nsu.fit.asbooster.saved.model.TracksRepository
import javax.inject.Inject

@ActivityScoped
class PlayerPreviewPresenter @Inject constructor(
    private val view: PlayerPreviewView,
    private val player: AudioPlayer,
    private val router: Router,
    private val viewItemsMapper: ViewItemsMapper,
    private val numberFormatter: NumberFormatter,
    private val repository: TracksRepository,
    private val uiScope: CoroutineScope,
    private val messageHelper: SnackbarMessageHelper,
    private val stringsProvider: StringsProvider,
    private val effectsManager: EffectsManager,
    private val playlistController: PlaybackControllerImpl,
    activity: AppCompatActivity
) {

    private lateinit var audioInfo: AudioInfo

    private val playerListener = object : AudioPlayer.Listener {
        override fun onPlay() {
            view.showPauseButton()
        }

        override fun onPause() {
            view.showPlayButton()
        }

        override fun onLoadingStart() {
            view.showProgress()
        }

        override fun onLoadingFinish() {
            view.hideProgress()
        }

        override fun onComplete() {
            view.showPlayButton()
        }

        override fun onProgress(progress: Int) {
            val elapsedDuration = numberFormatter.formatDuration(progress)
            view.setElapsed(elapsedDuration)
        }

        override fun onLoopingModeChanged(looping: Boolean) = Unit

        override fun onAudioChanged() {
            updatePlayingTrack()
        }
    }

    private fun updatePlayingTrack() {
        player.audio?.let {
            audioInfo = it
            with(view) {
                val audioItem = viewItemsMapper.trackToAudioItem(Track(
                    audioInfo,
                    effectsManager.effectsSettings
                ))
                show(audioItem)
            }
        }
    }

    private val viewListener = object : PlayerPreviewView.Listener {
        override fun onCloseClick() {
            view.hide()
            playlistController.stop()
        }

        override fun onFavoritesClick() {
            uiScope.launch {
                messageHelper.showMessage(stringsProvider.savedMessage)
                repository.saveTrack(Track(
                    audioInfo,
                    effectsManager.effectsSettings
                ))
            }
        }

        override fun onOpenClick() {
            router.openPlayer()
        }

        override fun onPlayClick() {
            if (player.playing) {
                player.pause()
                view.showPlayButton()
            } else {
                player.play()
                view.showPauseButton()
            }
        }
    }

    init {
        player.addListener(playerListener)
        view.listener = viewListener

        activity.lifecycle.addObserver(object : LifecycleObserver {

            @Suppress("unused")
            @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
            fun onDestroy() {
                player.removeListener(playerListener)
            }

        })

        updateInitialState()
    }

    private fun updateInitialState() {
        if (player.playing) {
            view.showPauseButton()
        }
        updatePlayingTrack()
    }
}
