package ru.nsu.fit.asbooster.player.preloader

import android.media.MediaPlayer
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import ru.nsu.fit.asbooster.repository.AudioRepository
import ru.nsu.fit.asbooster.repository.entity.AudioInfo
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PlayerPreloader @Inject constructor(
    private val uiScope: CoroutineScope,
    private val repository: AudioRepository
) {

    private val playersCache = hashMapOf<AudioInfo, PreloadingMediaPlayer>()

    /**
     * Start track preloading.
     * Do nothing if it is already preloading.
     */
    fun startPreloading(audioInfo: AudioInfo) {
        if (audioInfo in playersCache) {
            return
        }
        if (audioInfo.urlToStream == null) {
            return
        }
        val mediaPlayer = MediaPlayer().apply {
            setOnErrorListener { _, _, _ ->
                playersCache.remove(audioInfo)
                true
            }
            setOnPreparedListener {
                playersCache[audioInfo]?.prepared = true
            }
        }
        playersCache[audioInfo] = PreloadingMediaPlayer(mediaPlayer)
        uiScope.launch {
            repository.getStreamUrl(audioInfo.urlToStream)?.let { streamUrl ->
                mediaPlayer.setDataSource(streamUrl)
                mediaPlayer.prepareAsync()
            }
        }
    }

    fun stopPreloading(audioInfo: AudioInfo) {
        playersCache.remove(audioInfo)?.player?.release()
    }

    fun popPreloadingPlayer(audioInfo: AudioInfo) = playersCache.remove(audioInfo)

    fun clearAllPreloads() {
        playersCache.forEach { it.value.player.release() }
        playersCache.clear()
    }
}

class PreloadingMediaPlayer(val player: MediaPlayer, var prepared: Boolean = false)
