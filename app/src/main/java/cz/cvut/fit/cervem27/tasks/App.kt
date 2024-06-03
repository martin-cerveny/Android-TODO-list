package cz.cvut.fit.cervem27.tasks

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequest
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import cz.cvut.fit.cervem27.tasks.core.di.coreModule
import cz.cvut.fit.cervem27.tasks.core.di.workerModule
import cz.cvut.fit.cervem27.tasks.features.category.di.categoriesModule
import cz.cvut.fit.cervem27.tasks.features.notification.NotificationWorker
import cz.cvut.fit.cervem27.tasks.features.notification.notificationModule
import cz.cvut.fit.cervem27.tasks.features.notification.scheduleDailyDeadlineCheck
import cz.cvut.fit.cervem27.tasks.features.task.di.taskModule
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.workmanager.koin.workManagerFactory
import org.koin.core.context.startKoin
import java.util.Calendar
import java.util.concurrent.TimeUnit

class App : Application(){
    override fun onCreate() {
        super.onCreate()
        startKoin{
            androidContext(this@App)
            workManagerFactory()
            modules(categoriesModule, coreModule, taskModule, workerModule, notificationModule)
        }
        scheduleDailyDeadlineCheck(this)
    }
}


