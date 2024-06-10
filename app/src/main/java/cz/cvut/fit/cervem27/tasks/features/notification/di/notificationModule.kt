package cz.cvut.fit.cervem27.tasks.features.notification.di

import cz.cvut.fit.cervem27.tasks.features.notification.data.NotificationHelper
import cz.cvut.fit.cervem27.tasks.features.notification.data.NotificationPlannerWorker
import cz.cvut.fit.cervem27.tasks.features.notification.data.NotificationWorker
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.workmanager.dsl.worker
import org.koin.dsl.module

val notificationModule
    get() = module {
        single { NotificationHelper(get()) }
    }