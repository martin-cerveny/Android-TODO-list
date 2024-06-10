package cz.cvut.fit.cervem27.tasks.features.notification.data

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import cz.cvut.fit.cervem27.tasks.MainActivity
import cz.cvut.fit.cervem27.tasks.R
import cz.cvut.fit.cervem27.tasks.core.data.db.TasksDao
import cz.cvut.fit.cervem27.tasks.features.task.data.db.TimeDbConvertors
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
        title: String = context.getString(R.string.app_name),
        content: String = context.getString(R.string.deadline_notification_content),
        priority: Int = NotificationCompat.PRIORITY_HIGH
    ) {


        val intent = Intent(context, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
            context,
            0,
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

        if(! isDeadlineToday()){
            return
        }




        val builder = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(R.drawable.baseline_task_alt_24)
            .setContentTitle(title)
            .setContentText(content)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .setPriority(priority)

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


    // returns true if there's a deadlined planed for today (false otherwise)
    private suspend fun isDeadlineToday() : Boolean{
        val calendar = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }
        val start = calendar.time
        calendar.add(Calendar.DAY_OF_YEAR, 1)
        val end = calendar.time
        val deadlineTasks = tasksDao.getTasksByDeadline(TimeDbConvertors().dateToTimestamp(start)?:0,
            TimeDbConvertors().dateToTimestamp(end)?:0 )
        return deadlineTasks.isNotEmpty()
    }
}
