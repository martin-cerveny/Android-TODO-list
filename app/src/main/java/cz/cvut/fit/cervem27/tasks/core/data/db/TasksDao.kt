package cz.cvut.fit.cervem27.tasks.core.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import cz.cvut.fit.cervem27.tasks.features.category.data.db.DbCategory
import cz.cvut.fit.cervem27.tasks.features.task.data.db.DbTask
import cz.cvut.fit.cervem27.tasks.features.task.data.db.DbTaskWithCategory
import kotlinx.coroutines.flow.Flow


@Dao
interface TasksDao {

    @Query("SELECT * FROM categories")
    fun getCategoriesStream(): Flow<List<DbCategory>>

    @Insert
    suspend fun insertCategory(categories: List<DbCategory>)

    @Delete
    suspend fun deleteTask(task: DbTask)

    @Insert
    suspend fun insertTask(task: DbTask)
    @Transaction
    @Query("SELECT * FROM tasks ORDER BY deadline")
    fun getAllTasksWithCategoriesOrderedStream(): Flow<List<DbTaskWithCategory>>

}
