package cz.cvut.fit.cervem27.tasks.features.notification.data

import android.content.Context
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import java.util.concurrent.TimeUnit



fun scheduleNotificationsDaily(context: Context){

    // interval during which OneTimeWork is scheduled to display a notification
    // for the next day at 8am
    // 23 HOUR ensures
    // the period of 23 hours (instead of 1 day) would solve the case when the current
    // NotificationWorker would be executed and the next one would be planned at the same time
    // ExistingWorkPolicy.KEEP ensures ongoing work won't be disturbed
    // and the new NotificationWorker will be planned withing 23 hours


    val workRequest = PeriodicWorkRequestBuilder<NotificationPlannerWorker>(
        23, TimeUnit.HOURS
    ).build()

    WorkManager.getInstance(context).enqueueUniquePeriodicWork(
        "planner_worker",
        ExistingPeriodicWorkPolicy.KEEP,
        workRequest
    )
}


