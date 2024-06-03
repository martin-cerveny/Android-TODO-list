package cz.cvut.fit.cervem27.tasks.features.task.di

import org.koin.androidx.viewmodel.dsl.viewModelOf
import cz.cvut.fit.cervem27.tasks.features.task.presentation.createTask.CreateTaskViewModel
import cz.cvut.fit.cervem27.tasks.features.task.presentation.listTasks.TasksListViewModel
import cz.cvut.fit.cervem27.tasks.features.task.data.db.TaskLocalDataSource
import cz.cvut.fit.cervem27.tasks.features.task.data.TaskRepository
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val taskModule = module{

    factoryOf(::TaskLocalDataSource)

    singleOf(::TaskRepository)

    viewModelOf(::CreateTaskViewModel)
    viewModelOf(::TasksListViewModel)
}