package cz.cvut.fit.cervem27.tasks.features.notification.data

import android.content.Context
import android.util.Log
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequest
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import cz.cvut.fit.cervem27.tasks.features.notification.data.NotificationWorker
import java.util.Calendar
import java.util.concurrent.TimeUnit

fun scheduleDailyDeadlineCheck(context: Context){
    val currentDate = Calendar.getInstance()
    val dueDate = Calendar.getInstance()// Set Execution around 8 AM
    dueDate.set(Calendar.HOUR_OF_DAY, 8)
    dueDate.set(Calendar.MINUTE, 0)
    dueDate.set(Calendar.SECOND, 0)

    if (dueDate.before(currentDate)) {
        dueDate.add(Calendar.HOUR_OF_DAY, 24)
    }
    val timeDiff = dueDate.timeInMillis - currentDate.timeInMillis

    val workRequest = PeriodicWorkRequestBuilder<NotificationWorker>(
        1, TimeUnit.DAYS,
        30, TimeUnit.MINUTES
    )
        .setInitialDelay(timeDiff, TimeUnit.MILLISECONDS)
        .build()

    WorkManager.getInstance(context).enqueueUniquePeriodicWork(
        "TasksNotificationDeadlineWorker",
        ExistingPeriodicWorkPolicy.CANCEL_AND_REENQUEUE,
        workRequest
    )
}
