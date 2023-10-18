package com.example.androidapptest

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import kotlin.concurrent.thread

class TaskNotificationService: Service() {
    private val CHANNEL_ID = "Todo"
    private val NOTIFICATION_ID = 42
    private var isAlive = true

    companion object {
        fun startService(context: Context) {
            val startIntent = Intent(context, TaskNotificationService::class.java)
            ContextCompat.startForegroundService(context, startIntent)
        }

        fun stopService(context: Context) {
            val stopIntent = Intent(context, TaskNotificationService::class.java)
            context.stopService(stopIntent)
        }
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        createNotificationChannel()
        val onClickIntent = PendingIntent.getActivity(
            this,
            0, Intent(this, MainActivity::class.java), PendingIntent.FLAG_IMMUTABLE
        )
        val notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("Todo")
            .setContentText("loading...")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentIntent(onClickIntent)
            .setOngoing(true)
        startForeground(NOTIFICATION_ID, notification.build())

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        thread(start = true) {
            var i = 0
            while (isAlive) {
                val new_notification = notification
                    .setContentText(i.toString())
                    .build()
                notificationManager.notify(NOTIFICATION_ID, new_notification)

                i++
                Thread.sleep(1000)
            }
        }

        return super.onStartCommand(intent, flags, startId)
    }

    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    override fun onDestroy() {
        isAlive = false
        super.onDestroy()
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val serviceChannel = NotificationChannel(
                CHANNEL_ID,
                "Todo",
                NotificationManager.IMPORTANCE_DEFAULT)
            serviceChannel.description = "Todo"
            val manager = getSystemService(NotificationManager::class.java)
            manager!!.createNotificationChannel(serviceChannel)
        }
    }
}