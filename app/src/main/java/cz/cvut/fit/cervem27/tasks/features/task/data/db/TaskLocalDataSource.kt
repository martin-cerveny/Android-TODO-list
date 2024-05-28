package cz.cvut.fit.cervem27.tasks.features.task.data.db

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import cz.cvut.fit.cervem27.tasks.core.data.db.TasksDao
import cz.cvut.fit.cervem27.tasks.features.category.data.db.DbCategory
import cz.cvut.fit.cervem27.tasks.features.category.data.db.toDomain
import cz.cvut.fit.cervem27.tasks.features.category.domain.Category
import cz.cvut.fit.cervem27.tasks.features.category.domain.Icon
import cz.cvut.fit.cervem27.tasks.features.task.domain.Task
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.Date

class TaskLocalDataSource(private val tasksDao: TasksDao) {

    fun getTasks(): Flow<List<Task>> = tasksDao.getAllTasksWithCategoriesStream().map { dbTaskWithCategory ->
        dbTaskWithCategory.map { it.toDomain() }
    }

    suspend fun insert(task: Task) {

        tasksDao.insertTask(
            DbTask(
                taskId = task.taskId,
                categoryId = task.category.categoryId,
                taskName = task.name
            )
        )
    }


    private fun DbTaskWithCategory.toDomain(): Task {
        return Task(
            taskId = task.taskId,
            name = task.taskName,
            category = category.toDomain(),
            date = Date(1987),
            subtasks = emptyList()
        )
    }
}
