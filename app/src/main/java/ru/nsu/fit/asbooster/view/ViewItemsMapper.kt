package ru.nsu.fit.asbooster.view

import ru.nsu.fit.asbooster.R
import ru.nsu.fit.asbooster.formating.NumberFormatter
import ru.nsu.fit.asbooster.repository.WebImageProvider
import ru.nsu.fit.asbooster.repository.entity.AudioInfo
import ru.nsu.fit.asbooster.saved.model.Track
import ru.nsu.fit.asbooster.search.adapter.AudioItem
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ViewItemsMapper @Inject constructor(
    private val imageProvider: WebImageProvider,
    private val formatter: NumberFormatter
){

    fun audioInfoToAudioItems(audios: List<AudioInfo>) = audios.map {
       toAudioItem(it)
    }

    fun tracksToAudioItems(audios: List<Track>) = audios.map {
       toAudioItem(it.audioInfo)
    }

    private fun toAudioItem(audioInfo: AudioInfo) = with(audioInfo) {
        AudioItem(
            name,
            author,
            imageProvider.provideImage(
                smallImageUrl, miniImageUrl,
                R.drawable.track_list_item_placeholder_image
            ),
            formatter.formatDuration(duration),
            formatter.formatPlaybackCount(playbackCount),
            formatter.formatPostDate(postDate)
        )
    }

}