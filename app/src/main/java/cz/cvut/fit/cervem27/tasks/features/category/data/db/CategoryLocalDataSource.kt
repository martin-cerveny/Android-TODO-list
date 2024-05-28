package cz.cvut.fit.cervem27.tasks.features.category.data.db



import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import cz.cvut.fit.cervem27.tasks.core.data.db.TasksDao
import cz.cvut.fit.cervem27.tasks.features.category.domain.Category
import cz.cvut.fit.cervem27.tasks.features.category.domain.CategoryIcon
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class CategoryLocalDataSource(private val tasksDao: TasksDao) {

    fun getCategories(): Flow<List<Category>> = tasksDao.getCategoriesStream().map { dbCategory ->
        dbCategory.map { it.toDomain() }
    }

    suspend fun insert(categories: List<Category>) {

        tasksDao.insertCategory(categories.map { category ->
            DbCategory(
                categoryId = category.categoryId,
                categoryName = category.categoryName,
                iconUrl = category.categoryIcon.url,
                iconColor = category.categoryIcon.color.toArgb()
            )
        })
    }



}
fun DbCategory.toDomain(): Category {
    return Category(
        categoryId = categoryId,
        categoryName = categoryName,
        categoryIcon = CategoryIcon(url = iconUrl, color = Color(iconColor))
    )
}