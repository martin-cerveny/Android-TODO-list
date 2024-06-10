package cz.cvut.fit.cervem27.tasks.features.notification.data

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.WorkerParameters
import org.koin.core.component.KoinComponent
import java.util.Calendar
import java.util.concurrent.TimeUnit

class NotificationPlannerWorker(context: Context, workerParameters: WorkerParameters) : CoroutineWorker(context, workerParameters),
    KoinComponent {

    override suspend fun doWork(): Result {
        planNotification()
        return Result.success()
    }

    private fun planNotification() {

        // Set Execution around 8 AM
        val currentDate = Calendar.getInstance()
        val dueDate = Calendar.getInstance()
        dueDate.set(Calendar.HOUR_OF_DAY, 8)
        dueDate.set(Calendar.MINUTE, 0)
        dueDate.set(Calendar.SECOND, 0)

        if (dueDate.before(currentDate)) {
            dueDate.add(Calendar.DAY_OF_YEAR, 1)
        }

        val timeDiff = dueDate.timeInMillis - currentDate.timeInMillis

        val uniqueWorkRequest = OneTimeWorkRequestBuilder<NotificationWorker>()
            .setInitialDelay(timeDiff, TimeUnit.MILLISECONDS)
            .build()

        WorkManager.getInstance(applicationContext).enqueueUniqueWork(
            "notification_worker",
            ExistingWorkPolicy.KEEP,
            uniqueWorkRequest
        )
    }
}