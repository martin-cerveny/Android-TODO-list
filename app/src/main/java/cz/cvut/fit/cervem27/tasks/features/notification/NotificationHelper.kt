package cz.cvut.fit.cervem27.tasks.features.notification

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import cz.cvut.fit.cervem27.tasks.MainActivity
import cz.cvut.fit.cervem27.tasks.R
import cz.cvut.fit.cervem27.tasks.core.data.db.TasksDao
import cz.cvut.fit.cervem27.tasks.features.task.data.db.Converters
import kotlinx.coroutines.delay
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import java.util.Calendar

class NotificationHelper(
    private val tasksDao: TasksDao
) : KoinComponent {
    private val context: Context by inject()

    private var notificationId = 1
    private val channelId = context.packageName + "-deadline"

    init {
        createChannel()
    }

    suspend fun notifyUpcomingDeadlines(
        title: String = "Title",
        content: String = "Photo $notificationId uploading...",
        priority: Int = NotificationCompat.PRIORITY_HIGH
    ) {


        val intent = Intent(context, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
            context,
            7,
            intent,
            PendingIntent.FLAG_IMMUTABLE
        )
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }

        if(! deadlineInOneDay()){
            return
        }




        val builder = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(R.drawable.baseline_calendar_month_24)
            .setContentTitle(title)
            .setContentText(content)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .setPriority(priority)


        builder.setContentText("Done!")
        delay(100)
        with(NotificationManagerCompat.from(context)) {
            notify(notificationId, builder.build())
        }
        notificationId += 1
    }

    private fun createChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = context.getString(R.string.notification_title)
            val descriptionText = context.getString(R.string.notification_description)
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(channelId, name, importance).apply {
                description = descriptionText
            }
            val notificationManager: NotificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    private suspend fun deadlineInOneDay() : Boolean{
        Log.d("inhere", "searching deadlines")
        val calendar = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }
        val start = calendar.time
        calendar.add(Calendar.DAY_OF_YEAR, 1)
        val end = calendar.time
        val deadlineTasks = tasksDao.getTasksForNextDay(Converters().dateToTimestamp(start)?:0,
            Converters().dateToTimestamp(end)?:0 )
        return deadlineTasks.isNotEmpty()
    }
}
