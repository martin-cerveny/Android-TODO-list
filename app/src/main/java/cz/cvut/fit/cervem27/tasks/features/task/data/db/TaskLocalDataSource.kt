package cz.cvut.fit.cervem27.tasks.features.task.data.db

import cz.cvut.fit.cervem27.tasks.core.data.db.TasksDao
import cz.cvut.fit.cervem27.tasks.features.category.data.db.toDomain
import cz.cvut.fit.cervem27.tasks.features.task.domain.Task
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class TaskLocalDataSource(private val tasksDao: TasksDao) {

    fun getTasks(): Flow<List<Task>> = tasksDao.getAllTasksWithCategoriesOrderedStream().map { dbTaskWithCategory ->
        dbTaskWithCategory.map { it.toDomain() }
    }

    suspend fun insert(task: Task) {

        tasksDao.insertTask(
            DbTask(
                taskId = task.taskId,
                categoryId = task.category.categoryId,
                taskName = task.name,
                deadline = task.date
            )
        )
    }


    private fun DbTaskWithCategory.toDomain(): Task {
        return Task(
            taskId = task.taskId,
            name = task.taskName,
            category = category.toDomain(),
            date = task.deadline,
            subtasks = emptyList()
        )
    }
}
