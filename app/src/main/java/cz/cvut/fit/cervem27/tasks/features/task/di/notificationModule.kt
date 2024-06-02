package cz.cvut.fit.cervem27.tasks.features.task.di

import cz.cvut.fit.cervem27.tasks.features.task.data.notification.NotificationHelper
import org.koin.dsl.module

val notificationModule
    get() = module {
        single { NotificationHelper(get()) }
    }