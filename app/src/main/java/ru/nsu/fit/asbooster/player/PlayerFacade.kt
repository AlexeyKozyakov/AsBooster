package ru.nsu.fit.asbooster.player

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import ru.nsu.fit.asbooster.player.audio.AudioPlayer
import ru.nsu.fit.asbooster.player.effects.EffectsManager
import ru.nsu.fit.asbooster.player.playlist.PlayList
import ru.nsu.fit.asbooster.saved.model.Track
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PlayerFacade @Inject constructor(
    private val player: AudioPlayer,
    private val effectsManager: EffectsManager,
    private val uiScope: CoroutineScope
) {

    private val listeners = mutableListOf<Listener>()

    interface Listener {
        fun onTrackStarted(track: Track) = Unit

        fun onPlayListStarted(playList: PlayList) = Unit

        fun onPlayListDropped(playList: PlayList) = Unit
    }

    var playlist: PlayList? = null
    private set

    var track: Track? = null
    private set

    private val playerListener = object : AudioPlayer.Listener {
        override fun onComplete() {
            next()
        }
    }

    init {
        player.addListener(playerListener)
    }

    /**
     * Start playing track.
     */
    fun start(track: Track) {
        uiScope.launch {
            dropPlaylist()
            startTrack(track)
        }
    }

    /**
     * Start playing playlist.
     */
    fun start(playlist: PlayList) {
        uiScope.launch {
            dropPlaylist()
            this@PlayerFacade.playlist = playlist
            notify { onPlayListStarted(playlist) }
            val firstTrack = playlist.current() ?: return@launch
            startTrack(firstTrack)
        }
    }

    /**
     * Start next track.
     */
    fun next() {
        uiScope.launch {
            val nextTrack = playlist?.next() ?: return@launch
            startTrack(nextTrack)
        }
    }

    /**
     * Start previous track.
     */
    fun previous() {
        uiScope.launch {
            val previousTrack = playlist?.previous() ?: return@launch
            startTrack(previousTrack)
        }
    }

    fun stop() {
        player.reset()
        dropPlaylist()
    }

    fun addListener(listener: Listener) = listeners.add(listener)

    fun removeListener(listener: Listener) = listeners.add(listener)

    fun notify(event: Listener.() -> Unit) = listeners.forEach(event)

    private suspend fun startTrack(track: Track) {
        this.track = track
        notify { onTrackStarted(track) }
        player.start(track.audioInfo)
        effectsManager.effectsSettings = track.effectsInfo
    }

    private fun dropPlaylist() {
        playlist?.let {
            it.destroy()
            notify { onPlayListDropped(it) }
        }
        playlist = null
    }

}
