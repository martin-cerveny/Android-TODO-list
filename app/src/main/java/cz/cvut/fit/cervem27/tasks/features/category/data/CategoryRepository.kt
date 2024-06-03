package cz.cvut.fit.cervem27.tasks.features.category.data


import cz.cvut.fit.cervem27.tasks.features.category.data.db.CategoryLocalDataSource
import cz.cvut.fit.cervem27.tasks.features.category.data.api.IconRemoteDataSource
import cz.cvut.fit.cervem27.tasks.features.category.domain.Category

class CategoryRepository(
    private val remoteDataSource: IconRemoteDataSource,
    private val localDataSource: CategoryLocalDataSource
) {
    fun getCategories() = localDataSource.getCategories()
    suspend fun getCategory(id: Long) = localDataSource.getCategory(id)
    suspend fun insertCategory(category: Category) = localDataSource.insertCategory(category)

    suspend fun updateCategory(category: Category) = localDataSource.updateCategory(category)
    suspend fun deleteCategory(category: Category) = localDataSource.deleteCategory(category)
    suspend fun searchIcons(query: String) = remoteDataSource.searchIcons(query)

}