package cz.cvut.fit.cervem27.tasks.features.notification

import android.content.Context
import android.util.Log
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequest
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import java.util.Calendar
import java.util.concurrent.TimeUnit

fun scheduleDailyDeadlineCheck(context: Context){
    val currentDate = Calendar.getInstance()
    val dueDate = Calendar.getInstance()// Set Execution around 05:00:00 AM
    dueDate.set(Calendar.HOUR_OF_DAY, 19)
    dueDate.set(Calendar.MINUTE, 10)
    dueDate.set(Calendar.SECOND, 0)

    if (dueDate.before(currentDate)) {
        dueDate.add(Calendar.HOUR_OF_DAY, 24)
    }
    val timeDiff = dueDate.timeInMillis - currentDate.timeInMillis
    Log.d("timeDiff", "${timeDiff} ms")
    Log.d("flex", "${PeriodicWorkRequest.MIN_PERIODIC_FLEX_MILLIS} ms")

    val workRequest = PeriodicWorkRequestBuilder<NotificationWorker>(
        60, TimeUnit.MINUTES,
        PeriodicWorkRequest.MIN_PERIODIC_FLEX_MILLIS, TimeUnit.MILLISECONDS
    )
       // .setInitialDelay(timeDiff, TimeUnit.MILLISECONDS)
        .build()

    WorkManager.getInstance(context).enqueueUniquePeriodicWork(
        "TasksNotificationDeadlineWorker",
        ExistingPeriodicWorkPolicy.UPDATE, // todo
        workRequest
    )
}
