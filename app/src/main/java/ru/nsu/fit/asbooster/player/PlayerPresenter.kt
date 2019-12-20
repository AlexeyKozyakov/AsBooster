package ru.nsu.fit.asbooster.player

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import ru.nsu.fit.asbooster.repository.AudioRepository
import ru.nsu.fit.asbooster.repository.entity.AudioInfo
import ru.nsu.fit.asbooster.formating.NumberFormatter
import ru.nsu.fit.asbooster.di.ActivityScoped
import ru.nsu.fit.asbooster.player.audio.AudioPlayer
import ru.nsu.fit.asbooster.player.effects.EffectsManager
import ru.nsu.fit.asbooster.player.effects.ui.EffectItem
import ru.nsu.fit.asbooster.repository.StringsProvider
import ru.nsu.fit.asbooster.saved.model.Track
import ru.nsu.fit.asbooster.saved.model.TracksRepository
import ru.nsu.fit.asbooster.saved.model.entity.EffectInfo
import ru.nsu.fit.asbooster.view.ViewItemsMapper
import javax.inject.Inject

@ActivityScoped
class PlayerPresenter @Inject constructor(
    private val view: PlayerView,
    private val formatter: NumberFormatter,
    private val audioPlayer: AudioPlayer,
    private val uiScope: CoroutineScope,
    private val repository: AudioRepository,
    private val effectsManager: EffectsManager,
    private val tracksRepository: TracksRepository,
    private val stringsProvider: StringsProvider,
    private val viewItemsMapper: ViewItemsMapper
) {

    private lateinit var audioInfo: AudioInfo
    private lateinit var effectItems: List<EffectItem>


    fun onCreate(track: Track) {
        audioInfo = track.audioInfo
        val effects = track.effectsInfo
        initPlayer()
        initEffects(effects)
        initTracker()
    }

    fun onDestroy() {
        audioPlayer.destroy()
        effectsManager.destroy()
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
    fun onSeek(progress : Int){
        val time = progress*1000
        audioPlayer.seekTo(time)
        view.setElapsedTime(formatter.formatDuration(time))
    }

    fun onEffectForceChanged(position: Int, force: Int) {
        val effectItem = effectItems[position]
        effectsManager.setForce(effectItem.id, force)
    }

    fun onSave() {
        view.showMessage(stringsProvider.savedMessage)
        tracksRepository.saveTrack(Track(
            audioInfo,
            effectsManager.effectsSettings
        ))
    }

    private fun initPlayer() {
        view.setTrack(viewItemsMapper.audioInfoToTrackViewItem(audioInfo))

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

    private fun initTracker(){
        audioPlayer.progressListener = { progress ->
            val current = progress/1000
            view.updateProgressSeekBar(current)
            view.setElapsedTime(formatter.formatDuration(progress))
        }
    }

    private fun initEffects(effects: List<EffectInfo>) {
        effectsManager.effectsSettings = effects
        effectItems = viewItemsMapper.effectsToEffectItems(effectsManager.effects)
        view.showEffects(effectItems)
    }

}