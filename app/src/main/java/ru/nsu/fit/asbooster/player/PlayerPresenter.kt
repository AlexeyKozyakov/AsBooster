package ru.nsu.fit.asbooster.player

import android.content.Intent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import ru.nsu.fit.asbooster.base.SnackbarMessageHelper
import ru.nsu.fit.asbooster.base.navigation.Router
import ru.nsu.fit.asbooster.repository.entity.AudioInfo
import ru.nsu.fit.asbooster.formating.NumberFormatter
import ru.nsu.fit.asbooster.di.ActivityScoped
import ru.nsu.fit.asbooster.player.audio.AudioPlayer
import ru.nsu.fit.asbooster.player.effects.EffectsManager
import ru.nsu.fit.asbooster.repository.StringsProvider
import ru.nsu.fit.asbooster.saved.model.Track
import ru.nsu.fit.asbooster.saved.model.TracksRepository
import ru.nsu.fit.asbooster.saved.model.entity.EffectInfo
import ru.nsu.fit.asbooster.mappers.ViewItemsMapper
import javax.inject.Inject

@ActivityScoped
class PlayerPresenter @Inject constructor(
    private val view: PlayerView,
    private val formatter: NumberFormatter,
    private val audioPlayer: AudioPlayer,
    private val uiScope: CoroutineScope,
    private val effectsManager: EffectsManager,
    private val tracksRepository: TracksRepository,
    private val stringsProvider: StringsProvider,
    private val viewItemsMapper: ViewItemsMapper,
    private val messageHelper: SnackbarMessageHelper,
    private val playerFacade: PlayerFacade
) {

    private var audioInfo: AudioInfo? = null
    private lateinit var effectsInfo: List<EffectInfo>

    private val playerListener = object : AudioPlayer.Listener {
        override fun onProgress(progress: Int) {
            val current = progress / 1000
            view.updateProgressSeekBar(current)
            view.setElapsedTime(formatter.formatDuration(progress))
        }

        override fun onComplete() {
            view.showPlayButton()
        }

        override fun onLoadingStart(audioInfo: AudioInfo) {
            view.showProgress()
            updateCurrentTrack()
        }

        override fun onLoadingFinish() {
            view.hideProgress()
        }

        override fun onPLay() {
            view.showPauseButton()
        }

        override fun onPause() {
            view.showPlayButton()
        }

        override fun onLoopingModeChanged(looping: Boolean) {
            view.showLooping(looping)
        }
    }


    fun onCreate() {
        audioPlayer.addListener(playerListener)

        updateCurrentTrack()
    }

    fun onDestroy() {
        audioPlayer.removeListener(playerListener)
    }

    fun onPlayPauseClick() {
        if (audioPlayer.playing) {
            audioPlayer.pause()
        } else {
            audioPlayer.play()
        }
    }

    fun onSeek(progress: Int) {
        val time = progress * 1000
        audioPlayer.seekTo(time)
        view.setElapsedTime(formatter.formatDuration(time))
    }

    fun onEffectForceChanged(position: Int, force: Int) {
        val effectInfo = effectsInfo[position]
        effectsManager.setForce(effectInfo.id, force)
    }

    fun onSave() {
        audioInfo?.let { audioInfo ->
            messageHelper.showMessage(stringsProvider.savedMessage)
            uiScope.launch {
                tracksRepository.saveTrack(
                    Track(
                        audioInfo,
                        effectsManager.effectsSettings
                    )
                )
            }
        }
    }

    fun onLoopingClick() {
        audioPlayer.looping = !audioPlayer.looping
    }

    fun onNextClick() {
        playerFacade.next()
    }

    fun onPreviousClick() {
        playerFacade.previous()
    }

    private fun updateCurrentTrack() {
        audioInfo = audioPlayer.audio
        effectsInfo = effectsManager.effectsSettings
        showAudio()
        showEffects()
    }

    private fun showAudio() {
        audioInfo?.let {
            view.setTrack(viewItemsMapper.audioInfoToTrackViewItem(it))
        }
    }

    private fun showEffects() {
        view.showEffects(viewItemsMapper.effectsInfoToEffectItems(effectsInfo))
    }

}