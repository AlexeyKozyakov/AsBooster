package ru.nsu.fit.asbooster.player

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import ru.nsu.fit.asbooster.base.listenable.ListenableImpl
import ru.nsu.fit.asbooster.player.audio.AudioPlayer
import ru.nsu.fit.asbooster.player.audio.PlayListPlayer
import ru.nsu.fit.asbooster.player.effects.EffectsManager
import ru.nsu.fit.asbooster.player.playlist.EagerPlaylist
import ru.nsu.fit.asbooster.player.playlist.PlayList
import ru.nsu.fit.asbooster.player.preloader.PlayerPreloader
import ru.nsu.fit.asbooster.saved.model.Track
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PlaybackControllerImpl @Inject constructor(
    private val player: AudioPlayer,
    private val effectsManager: EffectsManager,
    private val uiScope: CoroutineScope,
    private val playerPreloader: PlayerPreloader
) : PlaybackController, ListenableImpl<PlaybackController.Listener>() {

    override var playlist: PlayList? = null
        private set

    override var track: Track? = null
        private set

    private val playerListener = object : AudioPlayer.Listener {
        override fun onComplete() {
            nextInternal(false)
        }
    }

    private var playerControlsPlayback = false

    init {
        player.addListener(playerListener)
    }


    /**
     * Start playing playlist.
     */
    override fun start(playlist: PlayList) {
        playerPreloader.stopAllPreloads()
        dropPlaylist()
        this@PlaybackControllerImpl.playlist = playlist
        uiScope.launch {
            val firstTrack = playlist.current() ?: return@launch
            notify { onPlayListStarted(playlist) }
            tryStartPlaylistByPlayer(playlist)
            startTrack(firstTrack)
        }
    }

    private suspend fun tryStartPlaylistByPlayer(playlist: PlayList) {
        if (!player.canPlay(playlist)) {
            return
        }
        playerControlsPlayback = true
        val audios = (playlist as EagerPlaylist).tracks().map { it.audioInfo }
        (player as PlayListPlayer).start(audios, playlist.currentPos)
    }

    /**
     * Start next track.
     */
    override fun next() = nextInternal(true)

    private fun nextInternal(byUser: Boolean) {
        playlist ?: return
        if (!byUser && player.looping) {
            return
        }
        uiScope.launch {
            if (byUser && playerControlsPlayback) {
                if (!(player as PlayListPlayer).next()) {
                    return@launch
                }
            }
            val nextTrack = playlist?.next() ?: return@launch
            startTrack(nextTrack)
        }
    }

    /**
     * Start previous track.
     */
    override fun previous() {
        playlist ?: return
        uiScope.launch {
            stopPreloadingNextTrack()
            if (playerControlsPlayback) {
                if (!(player as PlayListPlayer).previous()) {
                    return@launch
                }
            }
            val previousTrack = playlist?.previous() ?: return@launch
            startTrack(previousTrack)
        }
    }

    /**
     * Stop playing and reset state.
     */
    override fun stop() {
        player.reset()
        dropPlaylist()
        playerControlsPlayback = false
    }

    private suspend fun startTrack(track: Track) {
        this.track = track
        notify { onTrackStarted(track) }
        if (!playerControlsPlayback) {
            player.start(track.audioInfo)
        }
        effectsManager.effectsSettings = track.effectsInfo
        startPreloadingNextTrack()
    }

    private suspend fun startPreloadingNextTrack() {
        if (playerControlsPlayback) {
            return
        }
        playlist?.peekNext()?.let { playerPreloader.startPreloading(it.audioInfo) }
    }

    private suspend fun stopPreloadingNextTrack() {
        if (playerControlsPlayback) {
            return
        }
        playlist?.peekNext()?.let { playerPreloader.stopPreloading(it.audioInfo) }
    }

    private fun dropPlaylist() {
        playlist?.let {
            it.destroy()
            notify { onPlayListDropped(it) }
        }
        playlist = null
    }

    private fun AudioPlayer.canPlay(playlist: PlayList?) =
        this is PlayListPlayer && playlist is EagerPlaylist
}
