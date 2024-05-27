package cz.cvut.fit.cervem27.tasks.features.category.data.db



import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import cz.cvut.fit.cervem27.tasks.core.data.db.TasksDao
import cz.cvut.fit.cervem27.tasks.features.category.domain.Category
import cz.cvut.fit.cervem27.tasks.features.category.domain.Icon
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class CategoryLocalDataSource(private val tasksDao: TasksDao) {

    fun getCategories(): Flow<List<Category>> = tasksDao.getCategoriesStream().map { dbCategory ->
        dbCategory.map { it.toDomain() }
    }

    suspend fun insert(categories: List<Category>) {

        tasksDao.insert(categories.map { category ->
            DbCategory(
                id = category.categoryId,
                categoryName = category.categoryName,
                iconUrl = category.icon.url,
                iconColor = category.icon.color.toArgb()
            )
        })
    }


    private fun DbCategory.toDomain(): Category {
        return Category(
            categoryId = id,
            categoryName = categoryName,
            icon = Icon(url = iconUrl, color = Color(iconColor))
        )
    }
}
