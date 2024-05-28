package cz.cvut.fit.cervem27.tasks.features.task.data


import cz.cvut.fit.cervem27.tasks.features.category.data.db.CategoryLocalDataSource
import cz.cvut.fit.cervem27.tasks.features.task.data.db.TaskLocalDataSource
import cz.cvut.fit.cervem27.tasks.features.task.domain.Task

class TaskRepository(
    private val taskLocalDataSource: TaskLocalDataSource,
) {

    fun getTasks() = taskLocalDataSource.getTasks()



    suspend fun insertTask(task: Task) = taskLocalDataSource.insert(task)

}