package cz.cvut.fit.cervem27.tasks

import android.app.Application
import cz.cvut.fit.cervem27.tasks.core.di.coreModule
import cz.cvut.fit.cervem27.tasks.core.di.workerModule
import cz.cvut.fit.cervem27.tasks.features.category.di.categoriesModule
import cz.cvut.fit.cervem27.tasks.features.notification.di.notificationModule
import cz.cvut.fit.cervem27.tasks.features.notification.data.scheduleNotificationsDaily
import cz.cvut.fit.cervem27.tasks.features.task.di.taskModule
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.workmanager.koin.workManagerFactory
import org.koin.core.context.startKoin

class App : Application(){
    override fun onCreate() {
        super.onCreate()
        startKoin{
            androidContext(this@App)
            workManagerFactory()
            modules(categoriesModule, coreModule, taskModule, workerModule, notificationModule)
        }
        scheduleNotificationsDaily(this)
    }
}


