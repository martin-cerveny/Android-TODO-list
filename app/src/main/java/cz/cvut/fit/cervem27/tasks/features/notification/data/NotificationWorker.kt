package cz.cvut.fit.cervem27.tasks.features.notification.data

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import cz.cvut.fit.cervem27.tasks.features.notification.data.NotificationHelper
import org.koin.core.component.KoinComponent

class NotificationWorker(context: Context, private val notificationHelper: NotificationHelper, workerParameters: WorkerParameters) : CoroutineWorker(context, workerParameters), KoinComponent {

    override suspend fun doWork(): Result {
        showNotification()
        return Result.success()
    }

    private suspend fun showNotification() {
        notificationHelper.notifyUpcomingDeadlines()
    }
}