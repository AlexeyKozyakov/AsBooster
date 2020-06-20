package ru.nsu.fit.asbooster.search

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ru.nsu.fit.asbooster.repository.AudioRepository
import ru.nsu.fit.asbooster.di.FragmentScoped
import ru.nsu.fit.asbooster.mappers.ViewItemsMapper
import ru.nsu.fit.asbooster.player.PlayerFacade
import ru.nsu.fit.asbooster.player.audio.AudioPlayer
import ru.nsu.fit.asbooster.player.playlist.LinearPlaylist
import ru.nsu.fit.asbooster.player.playlist.PlayList
import ru.nsu.fit.asbooster.saved.model.audiosToTracks
import ru.nsu.fit.asbooster.saved.model.Track
import javax.inject.Inject

/**
 * Presenter for [SearchView]
 */

private const val NOT_FOUND = -1
private const val TYPE_TIMEOUT = 250L

@FragmentScoped
class SearchPresenter @Inject constructor(
    private val view: SearchView,
    private val audioRepository: AudioRepository,
    private val uiScope: CoroutineScope,
    private val viewItemsMapper: ViewItemsMapper,
    private val playerFacade: PlayerFacade,
    private val player: AudioPlayer
) {

    private var lastQueryChange = 0L
    private lateinit var tracks: List<Track>
    private lateinit var lastQuery: String

    private var playingPlayList: PlayList? = null
    private var playingPos: Int? = null

    private val playerFacadeListener = object : PlayerFacade.Listener {
        override fun onPlayListDropped(playList: PlayList) {
            if (playList == playingPlayList) {
                playingPlayList = null
                playingPos?.let {
                    view.hideAllInfo(it)
                }
                playingPos = null
            }
        }

        override fun onTrackStarted(track: Track) {
            playingPos?.let {
                view.hideAllInfo(it)
            }

            playingPlayList?.let {
                val trackPos = tracks.indexOf(track)

                if (trackPos != NOT_FOUND) {
                    view.showPlaying(trackPos)
                }

                playingPos = trackPos
            }
        }
    }

    private val playerListener = object : AudioPlayer.Listener {
        override fun onPLay() {
            playingPos?.let {
                view.showPlaying(it)
            }
        }

        override fun onPause() {
            playingPos?.let {
                view.showPaused(it)
            }
        }
    }

    fun onCreate() {
        view.showEmptyAudiosImage()
        playerFacade.addListener(playerFacadeListener)
        player.addListener(playerListener)
    }

    fun onDestroy() {
        playerFacade.removeListener(playerFacadeListener)
        player.removeListener(playerListener)
    }

    fun onQueryChanged(query: String) {
        lastQuery = query
        if (query.isEmpty()) {
            view.showEmptyAudiosImage()
            return
        }
        lastQueryChange = System.currentTimeMillis()
        uiScope.launch {
            delay(TYPE_TIMEOUT)
            if (System.currentTimeMillis() - lastQueryChange > TYPE_TIMEOUT) {
                launchQuery(query)
            }
        }
    }

    fun onAudioClick(index: Int) {
        val playList = LinearPlaylist(tracks, index)
        playingPlayList = playList
        playerFacade.start(playList)
    }

    private fun launchQuery(query: String) {
        if (query != lastQuery) {
            return
        }
        view.showProgress()
        uiScope.launch {
            val requestedAudios = audioRepository.searchAudios(query) ?: emptyList()
            //TODO: show placeholder if audios is isEmpty
            if (query == lastQuery) {
                tracks = audiosToTracks(requestedAudios)
                view.hideProgress()
                view.showAudios(viewItemsMapper.tracksToAudioItems(tracks))
            }
        }
    }
}
