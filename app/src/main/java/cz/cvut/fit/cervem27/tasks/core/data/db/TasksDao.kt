package cz.cvut.fit.cervem27.tasks.core.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import cz.cvut.fit.cervem27.tasks.features.category.data.db.DbCategory
import cz.cvut.fit.cervem27.tasks.features.task.data.db.DbTask
import cz.cvut.fit.cervem27.tasks.features.task.data.db.DbTaskWithCategory
import kotlinx.coroutines.flow.Flow


@Dao
interface TasksDao {
// Categories --------------------------------------------------------------------------------------
    @Query("SELECT * FROM categories")
    fun getCategoriesStream(): Flow<List<DbCategory>>

    @Query("SELECT * FROM categories WHERE categoryId = :id")
    suspend fun getCategory(id: Long): DbCategory

    @Insert
    suspend fun insertCategory(categories: DbCategory)

    @Update
    suspend fun updateCategory(character: DbCategory)
    @Delete
    suspend fun deleteCategory(task: DbCategory)
// TASKS -------------------------------------------------------------------------------------------
    @Transaction
    @Query("SELECT * FROM tasks ORDER BY deadline")
    fun getAllTasksWithCategoriesOrderedStream(): Flow<List<DbTaskWithCategory>>

    @Query("SELECT * FROM tasks WHERE taskId = :id")
    suspend fun getTask(id: Long): DbTaskWithCategory
    @Delete
    suspend fun deleteTask(task: DbTask)
    @Insert
    suspend fun insertTask(task: DbTask)

    @Update
    suspend fun updateTask(toDbTask: DbTask)

    @Query("SELECT * FROM tasks WHERE deadline BETWEEN :start AND :end")
    suspend fun getTasksForNextDay(start: Long, end: Long): List<DbTask>
}
