package cz.cvut.fit.cervem27.tasks.core.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import cz.cvut.fit.cervem27.tasks.features.category.data.db.DbCategory
import kotlinx.coroutines.flow.Flow


@Dao
interface TasksDao {

    @Query("SELECT * FROM categories")
    fun getCategoriesStream(): Flow<List<DbCategory>>

    @Insert
    suspend fun insert(categories: List<DbCategory>)

}
