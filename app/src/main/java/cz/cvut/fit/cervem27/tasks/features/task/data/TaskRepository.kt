package cz.cvut.fit.cervem27.tasks.features.task.data


import cz.cvut.fit.cervem27.tasks.features.task.data.db.TaskLocalDataSource
import cz.cvut.fit.cervem27.tasks.features.task.domain.Task

class TaskRepository(
    private val localDataSource: TaskLocalDataSource
) {

    fun getTasks() = localDataSource.getTasks()


    suspend fun insertTask(task: Task) = localDataSource.insert(task)

}