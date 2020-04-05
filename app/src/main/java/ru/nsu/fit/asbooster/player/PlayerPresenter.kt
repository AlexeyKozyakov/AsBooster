package ru.nsu.fit.asbooster.player

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import ru.nsu.fit.asbooster.base.SnackbarMessageHelper
import ru.nsu.fit.asbooster.repository.entity.AudioInfo
import ru.nsu.fit.asbooster.formating.NumberFormatter
import ru.nsu.fit.asbooster.di.ActivityScoped
import ru.nsu.fit.asbooster.player.audio.AudioPlayer
import ru.nsu.fit.asbooster.player.effects.EffectsManager
import ru.nsu.fit.asbooster.player.effects.preloaded.Effect
import ru.nsu.fit.asbooster.player.effects.ui.EffectItem
import ru.nsu.fit.asbooster.repository.StringsProvider
import ru.nsu.fit.asbooster.saved.model.Track
import ru.nsu.fit.asbooster.saved.model.TracksRepository
import ru.nsu.fit.asbooster.saved.model.entity.EffectInfo
import ru.nsu.fit.asbooster.mappers.ViewItemsMapper
import java.lang.RuntimeException
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
    private val messageHelper: SnackbarMessageHelper
) {

    private lateinit var audioInfo: AudioInfo
    private lateinit var effectItems: List<EffectItem>

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
        }

        override fun onLoadingFinish() {
            view.hideProgress()
        }
    }


    fun onCreate(track: Track?) {
        val effects = track?.effectsInfo
        audioInfo = track?.audioInfo ?: audioPlayer.currentAudio
        ?: throw RuntimeException("Player created but no track is provided")
        if (track != null && audioPlayer.currentAudio != audioInfo) {
            initWithNewTrack()
        } else {
            initWithCurrentTrack()
        }
        showAudio(audioInfo)
        if (effects != null) {
            initEffects(effects)
        }
        showEffects(effectsManager.effects)
        audioPlayer.addListener(playerListener)
    }

    fun onDestroy() {
        audioPlayer.removeListener(playerListener)
    }

    fun onPlayPauseClick() {
        if (!audioPlayer.loaded) {
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

    fun onSeek(progress: Int) {
        val time = progress * 1000
        audioPlayer.seekTo(time)
        view.setElapsedTime(formatter.formatDuration(time))
    }

    fun onEffectForceChanged(position: Int, force: Int) {
        val effectItem = effectItems[position]
        effectsManager.setForce(effectItem.id, force)
    }

    fun onSave() {
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

    private fun initWithCurrentTrack() {
        if (audioPlayer.playing) {
            view.showPauseButton()
        }
        if (audioPlayer.loading) {
            view.showProgress()
        }
    }

    private fun initWithNewTrack() {
        if (audioPlayer.loaded || audioPlayer.loading) {
            audioPlayer.reset()
        }

        uiScope.launch {
            audioPlayer.start(audioInfo)
            onPlayPauseClick()
        }
    }

    private fun showAudio(audioInfo: AudioInfo) {
        this.audioInfo = audioInfo
        view.setTrack(viewItemsMapper.audioInfoToTrackViewItem(audioInfo))
    }

    private fun initEffects(effects: List<EffectInfo>) {
        effectsManager.effectsSettings = effects
    }

    private fun showEffects(effects: List<Effect>) {
        effectItems = viewItemsMapper.effectsToEffectItems(effects)
        view.showEffects(effectItems)
    }

}