//package com.salem.amna.util
//
//import android.app.NotificationChannel
//import android.app.NotificationManager
//import android.app.PendingIntent
//import android.content.Context
//import android.content.Intent
//import android.os.Build
//import android.util.Log
//import android.widget.RemoteViews
//import androidx.core.app.NotificationCompat
//import com.google.android.gms.tasks.Task
//import com.google.firebase.messaging.FirebaseMessaging
//import com.google.firebase.messaging.FirebaseMessagingService
//import com.google.firebase.messaging.RemoteMessage
//import dagger.hilt.android.AndroidEntryPoint
//import kotlinx.coroutines.CoroutineScope
//import kotlinx.coroutines.SupervisorJob
//import kotlinx.coroutines.launch
//import net.moltaqa.talab_client.data.repository.local.preference.LocalePreference
//import net.moltaqa.talab_client.presentation.MainActivity
//import com.salem.amna.util.Constants.NOTIFICATION_CHANNEL_ID
//import com.salem.amna.util.Constants.NOTIFICATION_CHANNEL_NAME
//import javax.inject.Inject
//
//
//@AndroidEntryPoint
//class MyFirebaseMessagingService   : FirebaseMessagingService() {
//    private val TAG = "MyFirebaseMessagingServ"
//    val job = SupervisorJob()
//
//    @Inject
//    lateinit var localePreference: LocalePreference
//
//    override fun onMessageReceived(remoteMessage: RemoteMessage) {
//        super.onMessageReceived(remoteMessage)
//        if (remoteMessage.notification != null) {
//            Log.d(TAG, "onMessageReceived:title ${remoteMessage.notification!!.title}")
//            Log.d(TAG, "onMessageReceived:body ${remoteMessage.notification!!.body}")
//
//            showNotification(
//                remoteMessage.notification!!.title,
//                remoteMessage.notification!!.body
//            )
//        }
//    }
//
//    // Method to get the custom Design for the display of
//    // notification.
//    private fun getCustomDesign(
//        title: String?,
//        message: String?
//    ): RemoteViews {
//        val remoteViews = RemoteViews(
//            applicationContext.packageName,
//            R.layout.notification
//        )
//        remoteViews.setTextViewText(R.id.title, title)
//        remoteViews.setTextViewText(R.id.message, message)
//        remoteViews.setImageViewResource(
//            R.id.icon,
//            R.drawable.ic_talab_logo
//        )
//        return remoteViews
//    }
//
//    private fun showNotification(
//        title: String?,
//        message: String?
//    ) {
//        // Pass the intent to switch to the MainActivity
//        val intent = Intent(this, MainActivity::class.java)
//        // Assign channel ID
//
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
//
//        val pendingIntent = PendingIntent.getActivity(
//            this, 0, intent,
//            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
//        )
//
//
//        var builder: NotificationCompat.Builder = NotificationCompat.Builder(
//            applicationContext,
//            NOTIFICATION_CHANNEL_ID
//        )
//            .setSmallIcon(R.drawable.ic_talab_logo)
//            .setAutoCancel(true)
//            .setVibrate(
//                longArrayOf(
//                    1000, 1000, 1000,
//                    1000, 1000
//                )
//            )
//            .setOnlyAlertOnce(true)
//            .setContentIntent(pendingIntent)
//
//
//        builder = if (Build.VERSION.SDK_INT
//            >= Build.VERSION_CODES.LOLLIPOP_MR1
//        ) {
//            builder.setContent(
//                getCustomDesign(title, message)
//            )
//        } else {
//            builder.setContentTitle(title)
//                .setContentText(message)
//                .setSmallIcon(R.drawable.ic_talab_logo)
//        }
//
//        val notificationManager = getSystemService(
//            Context.NOTIFICATION_SERVICE
//        ) as NotificationManager
//
//        if (Build.VERSION.SDK_INT
//            >= Build.VERSION_CODES.O
//        ) {
//            val notificationChannel = NotificationChannel(
//                NOTIFICATION_CHANNEL_ID, NOTIFICATION_CHANNEL_NAME,
//                NotificationManager.IMPORTANCE_HIGH
//            )
//            notificationManager.createNotificationChannel(
//                notificationChannel
//            )
//        }
//        notificationManager.notify(0, builder.build())
//    }
//
//
//    override fun onNewToken(token: String) {
//        //super.onNewToken(token)
//        FirebaseMessaging.getInstance().token
//            .addOnCompleteListener { task: Task<String> ->
//                if (!task.isSuccessful) {
//                    Log.d(TAG, "onNewToken: ${task.exception?.message}")
//
//                    //handle token error
//                    return@addOnCompleteListener
//                }
//                val tokenResult = task.result
//                saveFcmToken(tokenResult)
//                Log.d(TAG, "onNewToken: $tokenResult")
//
//            }
//    }
//
//    private fun saveFcmToken(token: String) {
//        CoroutineScope(job).launch {
//            localePreference.saveFCMToken(token)
//        }
//    }
//
//    override fun onDestroy() {
//        job.cancel()
//        super.onDestroy()
//    }
//}