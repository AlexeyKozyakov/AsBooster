package ru.nsu.fit.asbooster.saved

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import ru.nsu.fit.asbooster.di.FragmentScoped
import ru.nsu.fit.asbooster.saved.model.TracksRepository
import ru.nsu.fit.asbooster.mappers.ViewItemsMapper
import ru.nsu.fit.asbooster.player.PlaybackController
import ru.nsu.fit.asbooster.player.audio.AudioPlayer
import ru.nsu.fit.asbooster.player.playlist.PlayList
import ru.nsu.fit.asbooster.player.playlist.RepositoryPlaylist
import ru.nsu.fit.asbooster.saved.model.Track
import javax.inject.Inject

@FragmentScoped
class SavedPresenter @Inject constructor(
    private val view: SavedView,
    private val tracksRepository: TracksRepository,
    private val viewItemsMapper: ViewItemsMapper,
    private val uiScope: CoroutineScope,
    private val playbackController: PlaybackController,
    private val player: AudioPlayer
) {

    private var currentPlayList: RepositoryPlaylist? = null
    private var currentPlayingPosition: Int? = null

    private val playListListener = object : PlaybackController.Listener {
        override fun onPlayListDropped(playList: PlayList) {
            if (playList != currentPlayList) {
                return
            }
            val pos = currentPlayingPosition ?: return
            view.hideAllInfo(pos)
            currentPlayingPosition = null
            currentPlayList = null
        }

        override fun onTrackStarted(track: Track) {
            currentPlayList?.let {
                uiScope.launch {
                    val trackPos = tracksRepository.getPosition(track) ?: return@launch
                    view.showPlaying(trackPos)
                    currentPlayingPosition = trackPos
                }
            }
        }

        override fun onPlayListStarted(playList: PlayList) {
            if (playList is RepositoryPlaylist) {
                currentPlayList = playList
                currentPlayingPosition = playList.currentPos
            }

        }
    }

    private val playerListener = object : AudioPlayer.Listener {
        override fun onPlay() {
            currentPlayingPosition?.let {
                view.showPlaying(it)
            }
        }

        override fun onPause() {
            currentPlayingPosition?.let {
                view.showPaused(it)
            }
        }
    }

    fun onCreate() {
        playbackController.addListener(playListListener)
        player.addListener(playerListener)

        uiScope.launch {
            view.showProgress()
            updateTracks()
            updatePlayingPlaylistAndTrack()
        }
    }

    private fun updatePlayingPlaylistAndTrack() {
        (playbackController.playlist as? RepositoryPlaylist)?.let {
            currentPlayList = it
            uiScope.launch {
                it.current()?.let { newTrack ->
                    tracksRepository.getPosition(newTrack)?.let { trackPosition ->
                        view.showPlaying(trackPosition)
                        currentPlayingPosition = trackPosition
                    }
                }
            }
        }
    }

    fun onDestroy() {
        tracksRepository.saveTrackListener = {}
        playbackController.removeListener(playListListener)
        player.removeListener(playerListener)
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

    private suspend fun updateTracks() {
        val tracks = tracksRepository.getTracks()
        if (tracks.any()) {
            view.hidePlaceholder()
        }
        view.hideProgress()
        view.showAudios(viewItemsMapper.tracksToAudioItems(tracks))
        initOnSaveTrackListener()
    }

    private fun initOnSaveTrackListener() {
        tracksRepository.saveTrackListener = { track ->
            view.hidePlaceholder()
            view.addAudioItem(viewItemsMapper.trackToAudioItem(track))
        }
    }

    fun onAudioClick(position: Int) {
        val playList = RepositoryPlaylist(tracksRepository, uiScope, position)
        view.showPlaying(position)
        playbackController.start(playList)
    }

}
