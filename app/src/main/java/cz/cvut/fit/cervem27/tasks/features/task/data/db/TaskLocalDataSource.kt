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

    suspend fun getTask(id: Long) = tasksDao.getTask(id).toDomain()

    suspend fun insert(task: Task) = tasksDao.insertTask(task.toDbTask())
    suspend fun update(task: Task) = tasksDao.updateTask(task.toDbTask())
    suspend fun delete(task: Task) = tasksDao.deleteTask(task.toDbTask())


    private fun Task.toDbTask(): DbTask{
        return  DbTask(
            taskId = taskId,
            categoryId = category?.categoryId,
            taskName = name,
            deadline = date
        )
    }
    private fun DbTaskWithCategory.toDomain(): Task {
        return Task(
            taskId = task.taskId,
            name = task.taskName,
            category = category?.toDomain(),
            date = task.deadline,
        )
    }

}
