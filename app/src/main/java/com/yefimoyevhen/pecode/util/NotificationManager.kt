package com.yefimoyevhen.pecode.util

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.os.Build
import androidx.annotation.DrawableRes
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.yefimoyevhen.pecode.view.ARG_POSITION_KEY
import com.yefimoyevhen.pecode.view.MainActivity
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

const val CHANNEL_ID = "1"
const val PENDING_REQUEST_ID = 0

@Singleton
class NotificationManager @Inject constructor
    (@ApplicationContext val context: Context) {

    fun sendNotification(
        value: Int = 1,
        @DrawableRes
        smallIcon: Int,
        largeIcon: Bitmap,
        title: String,
        text: String
    ) {
        createNotificationChannel(title, text)
        with(NotificationManagerCompat.from(context)) {
            notify(value, buildNotification(value, smallIcon, largeIcon, title, text))
        }
    }


    private fun createNotificationChannel(title: String, text: String) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, title, importance)
                .apply {
                    description = text
                }
            val notificationManager: NotificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun buildNotification(
        value: Int = 1,
        @DrawableRes
        smallIcon: Int,
        largeIcon: Bitmap,
        title: String,
        text: String
    ) = NotificationCompat.Builder(context, CHANNEL_ID)
        .setSmallIcon(smallIcon)
        .setContentTitle(title)
        .setOnlyAlertOnce(true)
        .setAutoCancel(true)
        .setContentIntent(createPangingIntent(value))
        .setContentText("$text $value")
        .setLargeIcon(largeIcon)
        .setPriority(NotificationCompat.PRIORITY_DEFAULT).build()


    private fun createPangingIntent(value: Int): PendingIntent {
        val notificationIntent = Intent(context, MainActivity::class.java)
            .apply {
                flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
                putExtra(ARG_POSITION_KEY, value)
            }
        return PendingIntent.getActivity(
            context,
            PENDING_REQUEST_ID,
            notificationIntent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )
    }
}