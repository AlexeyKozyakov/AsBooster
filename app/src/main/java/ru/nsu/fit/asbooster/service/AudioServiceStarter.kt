package ru.nsu.fit.asbooster.service

import android.app.Application
import android.content.Intent
import ru.nsu.fit.asbooster.player.audio.AudioPlayer
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AudioServiceStarter @Inject constructor(
    private val application: Application,
    private val player: AudioPlayer
) {

    private var serviceRunning = false

    private val audioServiceIntent = Intent(application, AudioService::class.java)

    private val playerListener = object : AudioPlayer.Listener {
        override fun onAudioChanged() {
            if (player.audio != null && !serviceRunning) {
                startAudioService()
                return
            }

            if (player.audio == null && serviceRunning) {
                stopAudioService()
            }
        }
    }

    init {
        player.addListener(playerListener)
    }


    private fun startAudioService() {
        serviceRunning = true
        application.startService(audioServiceIntent)
    }

    private fun stopAudioService() {
        serviceRunning = false
        application.stopService(audioServiceIntent)
    }
}
