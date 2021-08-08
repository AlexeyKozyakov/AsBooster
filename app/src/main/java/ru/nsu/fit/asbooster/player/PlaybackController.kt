package ru.nsu.fit.asbooster.player

import ru.nsu.fit.asbooster.base.listenable.Listenable
import ru.nsu.fit.asbooster.player.playlist.PlayList
import ru.nsu.fit.asbooster.saved.model.Track

/**
 * Used to start playlists and listen to playback events.
 */
interface PlaybackController : Listenable<PlaybackController.Listener> {

    interface Listener {
        fun onTrackChanged(track: Track, previous: Track?) = Unit

        fun onPlayListStarted(playList: PlayList) = Unit

        fun onPlayListDropped(playList: PlayList) = Unit
    }

    /**
     * Current playing playlist.
     */
    val playlist: PlayList?

    /**
     * Current playing track.
     */
    val track: Track?

    /**
     * Start playing playlist.
     */
    fun start(playlist: PlayList)

    /**
     * Start next track.
     */
    fun next()

    /**
     * Start previous track.
     */
    fun previous()

    /**
     * Stop playing and reset state.
     */
    fun stop()
}
