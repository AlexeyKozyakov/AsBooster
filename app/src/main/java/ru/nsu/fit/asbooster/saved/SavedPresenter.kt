package ru.nsu.fit.asbooster.saved

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import ru.nsu.fit.asbooster.di.FragmentScoped
import ru.nsu.fit.asbooster.player.effects.EffectsManager
import ru.nsu.fit.asbooster.saved.model.Track
import ru.nsu.fit.asbooster.saved.model.TracksRepository
import ru.nsu.fit.asbooster.view.ViewItemsMapper
import java.text.FieldPosition
import javax.inject.Inject

@FragmentScoped
class SavedPresenter @Inject constructor(
    private val view: SavedView,
    private val tracksRepository: TracksRepository,
    private val viewItemsMapper: ViewItemsMapper,
    private val uiScope: CoroutineScope
) {

    private var tracks = listOf<Track>()

    fun onShow() {
        if (tracks.isEmpty()) {
            view.showProgress()
        }
        uiScope.launch {
            tracks = tracksRepository.getTracks()
            view.hideProgress()
            view.showAudios(viewItemsMapper.tracksToAudioItems(tracks))
        }
    }

    fun onAudioClick(position: Int) {
        val track = tracks[position]

    }

}