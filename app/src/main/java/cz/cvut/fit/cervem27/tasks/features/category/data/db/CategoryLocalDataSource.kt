package cz.cvut.fit.cervem27.tasks.features.category.data.db



import android.database.sqlite.SQLiteConstraintException
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import cz.cvut.fit.cervem27.tasks.core.data.db.TasksDao
import cz.cvut.fit.cervem27.tasks.features.category.domain.Category
import cz.cvut.fit.cervem27.tasks.features.category.domain.CategoryIcon
import cz.cvut.fit.cervem27.tasks.features.category.presentation.categoriesCreate.Constants
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class CategoryLocalDataSource(private val tasksDao: TasksDao) {

    fun getCategories(): Flow<List<Category>> = tasksDao.getCategoriesStream().map { dbCategory ->
        dbCategory.map { it.toDomain() }
    }

    suspend fun insertCategory(category: Category) {

        tasksDao.insertCategory(
            category.toDatabase()
        )
    }

    suspend fun updateCategory(category: Category) = tasksDao.updateCategory(category.toDatabase())


    suspend fun getCategory(id: Long): Category = tasksDao.getCategory(id).toDomain()

    suspend fun deleteCategory(category: Category): Boolean {
        return try {
            tasksDao.deleteCategory(category.toDatabase())
            true
        } catch (e: SQLiteConstraintException) {
            false
        }
    }

    private fun Category.toDatabase(): DbCategory{
        return DbCategory(
            categoryId = categoryId,
            categoryName = categoryName,
            iconUrl = categoryIcon.url,
            iconHue = categoryIcon.colorHue
        )
    }


}
fun DbCategory.toDomain(): Category {
    return Category(
        categoryId = categoryId,
        categoryName = categoryName,
        categoryIcon = CategoryIcon(url = iconUrl, colorHue = iconHue)
    )
}