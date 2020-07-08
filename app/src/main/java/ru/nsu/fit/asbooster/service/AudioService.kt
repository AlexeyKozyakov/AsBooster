package ru.nsu.fit.asbooster.service

import android.app.*
import android.content.Context
import android.content.Intent
import ru.nsu.fit.asbooster.R
import ru.nsu.fit.asbooster.base.App
import ru.nsu.fit.asbooster.player.PlayerActivity
import ru.nsu.fit.asbooster.player.audio.AudioPlayer

private const val NOTIFICATION_CHANNEL_ID = "As booster player notification"
private const val NOTIFICATION_ID = 10

class AudioService : Service() {

    private val player get() = (application as App).component.value.player()

    private val notificationManager = lazy {
        getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    }

    private val playerListener = object : AudioPlayer.Listener {
        override fun onAudioChanged() {
            val notification = createCurrentTrackNotification()
            notificationManager.value.notify(NOTIFICATION_ID, notification)
        }
    }

    private fun createCurrentTrackNotification() =
        Notification.Builder(this, NOTIFICATION_CHANNEL_ID)
            .setSmallIcon(R.drawable.icon_play)
            .setContentTitle(player.audio?.author ?: "")
            .setContentText(player.audio?.name)
            .setStyle(Notification.MediaStyle())
            .setContentIntent(
                TaskStackBuilder.create(this).run {
                    addNextIntentWithParentStack(PlayerActivity.intentToLaunch(application))
                    getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT)
                }
            )
            .build()

    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()
        player.addListener(playerListener)
        startForeground(NOTIFICATION_ID, createCurrentTrackNotification())
    }

    override fun onDestroy() {
        player.removeListener(playerListener)
    }

    override fun onBind(intent: Intent?) = null

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int) = START_STICKY

    private fun createNotificationChannel() {
        val channel = NotificationChannel(
            NOTIFICATION_CHANNEL_ID,
            NOTIFICATION_CHANNEL_ID,
            NotificationManager.IMPORTANCE_LOW
        )
        notificationManager.value.createNotificationChannel(channel)
    }
}
