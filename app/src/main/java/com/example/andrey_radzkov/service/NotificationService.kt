package com.example.andrey_radzkov.service

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Handler
import android.provider.Settings
import android.support.v4.app.NotificationCompat
import android.support.v4.app.TaskStackBuilder
import android.support.v4.content.ContextCompat
import com.example.andrey_radzkov.NavigationActivity
import com.example.andrey_radzkov.R

/**
 * @author Radzkov Andrey
 */
class NotificationService {
    fun sendDelayedHotification(title: String, text: String, applicationContext: Context) {
        val handler = Handler()
        handler.postDelayed({
            val notificationBuilder = NotificationCompat.Builder(applicationContext)
                    .setSmallIcon(R.drawable.ic_launcher_background)
                    .setContentTitle("Supplyon Pid Registraction")
                    .setContentText("Connect with 'Seller' has been activated")
                    .setAutoCancel(true)
                    .setVibrate(longArrayOf(150, 100, 150, 100))
                    .setSound(Settings.System.DEFAULT_NOTIFICATION_URI)
                    .setLights(Color.RED, 3000, 3000)
                    .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                    .setPriority(NotificationCompat.PRIORITY_MAX)
            // Creates an explicit intent for an Activity in your app
            val resultIntent = Intent(applicationContext, NavigationActivity::class.java)

            // The stack builder object will contain an artificial back stack for the
            // started Activity.
            // This ensures that navigating backward from the Activity leads out of
            // your application to the Home screen.
            val stackBuilder = TaskStackBuilder.create(applicationContext)
            // Adds the back stack for the Intent (but not the Intent itself)
            stackBuilder.addParentStack(NavigationActivity::class.java)
            // Adds the Intent that starts the Activity to the top of the stack
            stackBuilder.addNextIntent(resultIntent)
            val resultPendingIntent = stackBuilder.getPendingIntent(
                    0,
                    PendingIntent.FLAG_UPDATE_CURRENT
            )
            notificationBuilder.setContentIntent(resultPendingIntent)
            val mNotificationManager = ContextCompat.getSystemService(applicationContext, NotificationManager::class.java) as NotificationManager
            // mId allows you to update the notification later on.
            val id = title.hashCode() + text.hashCode()
            val notification = notificationBuilder.build()

            mNotificationManager.notify(id, notification)
        }, 3000)
    }
}