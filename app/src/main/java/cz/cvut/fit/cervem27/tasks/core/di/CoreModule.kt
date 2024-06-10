package cz.cvut.fit.cervem27.tasks.core.di

import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.ktx.Firebase
import cz.cvut.fit.cervem27.tasks.core.data.db.TasksDatabase
import cz.cvut.fit.cervem27.tasks.features.category.data.db.CategoryLocalDataSource
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val coreModule = module {
    single { TasksDatabase.newInstance(androidContext()) }
    single{get<TasksDatabase>().tasksDao()}

    singleOf(FirebaseAnalytics::getInstance)

}