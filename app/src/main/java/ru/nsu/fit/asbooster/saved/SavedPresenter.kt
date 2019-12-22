package ru.nsu.fit.asbooster.saved

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import ru.nsu.fit.asbooster.di.FragmentScoped
import ru.nsu.fit.asbooster.saved.model.Track
import ru.nsu.fit.asbooster.saved.model.TracksRepository
import ru.nsu.fit.asbooster.search.navigation.Router
import ru.nsu.fit.asbooster.view.ViewItemsMapper
import javax.inject.Inject

@FragmentScoped
class SavedPresenter @Inject constructor(
    private val view: SavedView,
    private val tracksRepository: TracksRepository,
    private val viewItemsMapper: ViewItemsMapper,
    private val uiScope: CoroutineScope,
    private val router: Router
) {

    private var tracks = listOf<Track>()

    fun onCreate() {
        view.showProgress()
    }

    fun onShow() {
        view.hidePlaceholder()
        if (tracks.isEmpty()) {
            view.showProgress()
        } else {
            view.hideProgress()
        }
        updateTracks()
    }

    fun onSwipe(position: Int){
        tracksRepository.deleteTrack(tracks[position])
        view.removeTrackItem(position)
    }

    fun onMove(from: Int, to: Int) {
        tracksRepository.move(tracks[from], tracks[to])
        view.moveTrackItem(from, to)
    }

    private fun updateTracks() {
        uiScope.launch {
            tracks = tracksRepository.getTracks()
            view.hideProgress()
            if (tracks.isEmpty()) {
                view.showPlaceholder()
                return@launch
            }
            view.hidePlaceholder()
            view.showAudios(viewItemsMapper.tracksToAudioItems(tracks))
        }
    }

    fun onAudioClick(position: Int) {
        val track = tracks[position]
        router.openPlayer(track)
    }

}