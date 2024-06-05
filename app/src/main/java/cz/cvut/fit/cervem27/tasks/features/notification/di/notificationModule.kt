package cz.cvut.fit.cervem27.tasks.features.notification.di

import cz.cvut.fit.cervem27.tasks.features.notification.data.NotificationHelper
import org.koin.dsl.module

val notificationModule
    get() = module {
        single { NotificationHelper(get()) }
    }