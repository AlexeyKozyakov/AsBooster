package ru.nsu.fit.asbooster.saved

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import ru.nsu.fit.asbooster.di.FragmentScoped
import ru.nsu.fit.asbooster.saved.model.TracksRepository
import ru.nsu.fit.asbooster.base.navigation.Router
import ru.nsu.fit.asbooster.mappers.ViewItemsMapper
import javax.inject.Inject

@FragmentScoped
class SavedPresenter @Inject constructor(
    private val view: SavedView,
    private val tracksRepository: TracksRepository,
    private val viewItemsMapper: ViewItemsMapper,
    private val uiScope: CoroutineScope,
    private val router: Router
) {

    fun onCreate() {
        uiScope.launch {
            view.showProgress()
            updateTracks()
        }
    }

    fun onDestroy() {
        tracksRepository.saveTrackListener = {}
    }

    fun onSwipe(position: Int) {
        uiScope.launch {
            tracksRepository.deleteTrack(position)
            view.removeAudioItem(position)
            if (tracksRepository.isEmpty()) {
                view.showPlaceholder()
            }
        }
    }

    fun onMove(from: Int, to: Int) {
        uiScope.launch {
            with(tracksRepository) {
                move(getTrack(from), getTrack(to))
                view.moveAudioItem(from, to)
            }
        }
    }

    private fun updateTracks() {
        uiScope.launch {
            val tracks = tracksRepository.getTracks()
            if (tracks.any()) {
                view.hidePlaceholder()
            }
            view.hideProgress()
            view.showAudios(viewItemsMapper.tracksToAudioItems(tracks))
            initOnSaveTrackListener()
        }
    }

    private fun initOnSaveTrackListener() {
        tracksRepository.saveTrackListener = { track ->
            view.hidePlaceholder()
            view.addAudioItem(viewItemsMapper.trackToAudioItem(track))
        }
    }

    fun onAudioClick(position: Int) {
        uiScope.launch {
            val track = tracksRepository.getTrack(position)
            router.openPlayer(track)
        }
    }

}