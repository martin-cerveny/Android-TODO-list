package cz.cvut.fit.cervem27.tasks.features.notification

import cz.cvut.fit.cervem27.tasks.features.notification.NotificationHelper
import org.koin.dsl.module

val notificationModule
    get() = module {
        single { NotificationHelper(get()) }
    }