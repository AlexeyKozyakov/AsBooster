package ru.nsu.fit.asbooster.mappers

import ru.nsu.fit.asbooster.R
import ru.nsu.fit.asbooster.formating.NumberFormatter
import ru.nsu.fit.asbooster.player.TrackViewItem
import ru.nsu.fit.asbooster.player.effects.EffectImageProvider
import ru.nsu.fit.asbooster.player.effects.EffectNameProvider
import ru.nsu.fit.asbooster.player.effects.preloaded.Effect
import ru.nsu.fit.asbooster.player.effects.ui.EffectItem
import ru.nsu.fit.asbooster.repository.WebImageProvider
import ru.nsu.fit.asbooster.repository.entity.AudioInfo
import ru.nsu.fit.asbooster.saved.model.Track
import ru.nsu.fit.asbooster.saved.model.entity.EffectInfo
import ru.nsu.fit.asbooster.audios.view.AudioItem
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ViewItemsMapper @Inject constructor(
    private val imageProvider: WebImageProvider,
    private val formatter: NumberFormatter,
    private val effectNameProvider: EffectNameProvider,
    private val effectImageProvider: EffectImageProvider
){

    fun audioInfoToAudioItems(audios: List<AudioInfo>) = audios.map {
       toAudioItem(it)
    }.toMutableList()

    fun tracksToAudioItems(audios: List<Track>) = audios.map {
       toAudioItem(it.audioInfo)
    }.toMutableList()

    fun audioInfoToTrackViewItem(audioInfo: AudioInfo) = with(audioInfo) {
        TrackViewItem(
            name,
            author,
            imageProvider.provideImage(bigImageUrl, miniImageUrl),
            formatter.formatDuration(duration),
            audioInfo.duration/1000
        )
    }

    fun effectsToEffectItems(effects: List<Effect>) = effects.map {
        EffectItem(
            it.id,
            effectNameProvider.provideEffectName(it),
            effectImageProvider.provideEffectImage(it),
            it.force
        )
    }

    fun effectsInfoToEffectItems(effectsInfo: List<EffectInfo>) = effectsInfo.map {
        EffectItem(
            it.id,
            effectNameProvider.provideEffectName(it.id),
            effectImageProvider.provideEffectImage(it.id),
            it.force
        )
    }

    fun trackToAudioItem(track: Track) = toAudioItem(track.audioInfo)

    private fun toAudioItem(audioInfo: AudioInfo) = with(audioInfo) {
        AudioItem(
            name,
            author,
            imageProvider.provideImage(
                imageUrl = smallImageUrl,
                placeholder = R.drawable.track_list_item_placeholder_image
            ),
            formatter.formatDuration(duration),
            formatter.formatPlaybackCount(playbackCount),
            formatter.formatPostDate(postDate)
        )
    }

}