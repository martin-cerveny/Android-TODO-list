package cz.cvut.fit.cervem27.tasks.core.di

import cz.cvut.fit.cervem27.tasks.features.notification.data.NotificationWorker
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.workmanager.dsl.worker
import org.koin.dsl.module

val workerModule = module {
    worker { NotificationWorker(androidContext(), get(), get()) }
}