package ru.nsu.fit.asbooster.player.audio

import ru.nsu.fit.asbooster.repository.entity.AudioInfo

/**
 * Player with capability of playing playlists.
 */
interface PlayListPlayer {
    /**
     * Start playlist.
     */
    fun start(audioInfos: List<AudioInfo>, startPos: Int = 0)

    /**
     * Next track.
     * Return true if track is successfully changed.
     */
    fun next(): Boolean

    /**
     * Previous track.
     * Return true if track is successfully changed.
     */
    fun previous(): Boolean
}
