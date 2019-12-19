package ru.nsu.fit.asbooster.player

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ru.nsu.fit.asbooster.repository.AudioRepository
import ru.nsu.fit.asbooster.repository.WebImageProvider
import ru.nsu.fit.asbooster.repository.entity.AudioInfo
import ru.nsu.fit.asbooster.formating.NumberFormatter
import ru.nsu.fit.asbooster.di.ActivityScoped
import ru.nsu.fit.asbooster.player.audio.AudioPlayer
import ru.nsu.fit.asbooster.player.effects.preloaded.Effect
import ru.nsu.fit.asbooster.player.effects.EffectImageProvider
import ru.nsu.fit.asbooster.player.effects.EffectNameProvider
import ru.nsu.fit.asbooster.player.effects.EffectsManager
import ru.nsu.fit.asbooster.player.effects.ui.EffectItem
import javax.inject.Inject

@ActivityScoped
class PlayerPresenter @Inject constructor(
    private val view: PlayerView,
    private val imageProvider: WebImageProvider,
    private val formatter: NumberFormatter,
    private val audioPlayer: AudioPlayer,
    private val uiScope: CoroutineScope,
    private val repository: AudioRepository,
    private val effectImageProvider: EffectImageProvider,
    private val effectNameProvider: EffectNameProvider,
    private val effectsManager: EffectsManager
) {

    private lateinit var audioInfo: AudioInfo
    private lateinit var effectItems: List<EffectItem>


    fun onCreate(audio: AudioInfo) {
        audioInfo = audio
        initPlayer()
        initEffects()
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
        val time = audioInfo.duration*progress/100
        audioPlayer.seekTo(time)
    }

    fun onEffectForceChanged(position: Int, force: Int) {
        val effectItem = effectItems[position]
        effectsManager.setForce(effectItem.id, force)
    }

    private fun initPlayer() {
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

    private fun initTracker(){
        audioPlayer.progressListener = { progress ->
            val current = progress * 100 / audioInfo.duration
            view.updateProgressSeekBar(current)
            view.setElapsedTime(formatter.formatDuration(progress))
        }
    }

    private fun initEffects() {
        effectItems = toEffectItems(effectsManager.effects)
        view.showEffects(effectItems)
    }

    private fun toTrackViewItem(audioInfo: AudioInfo) = with(audioInfo) {
        TrackViewItem(
            name,
            author,
            imageProvider.provideImage(bigImageUrl, miniImageUrl),
            formatter.formatDuration(duration)
        )
    }

    private fun toEffectItems(effects: List<Effect>) = effects.map {
        EffectItem(
            it.id,
            effectNameProvider.provideEffectName(it),
            effectImageProvider.provideEffectImage(it),
            it.force
        )
    }

}