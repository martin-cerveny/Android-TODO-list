package cz.cvut.fit.cervem27.tasks

import android.app.Application
import android.util.Log
import cz.cvut.fit.cervem27.tasks.core.di.coreModule
import cz.cvut.fit.cervem27.tasks.features.category.di.categoriesModule
import cz.cvut.fit.cervem27.tasks.features.task.di.taskModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
class App : Application(){
    override fun onCreate() {
        super.onCreate()
        startKoin{
            androidContext(this@App)
            modules(categoriesModule, coreModule, taskModule)
        }
    }
}