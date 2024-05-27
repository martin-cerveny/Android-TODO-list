package cz.cvut.fit.cervem27.tasks.features.category.data


import cz.cvut.fit.cervem27.tasks.features.category.data.db.CategoryLocalDataSource
import cz.cvut.fit.cervem27.tasks.features.category.data.db.DbCategory
import cz.cvut.fit.cervem27.tasks.features.category.data.api.IconRemoteDataSource
import cz.cvut.fit.cervem27.tasks.features.category.domain.Category

class CategoryRepository(
    private val remoteDataSource: IconRemoteDataSource,
    private val localDataSource: CategoryLocalDataSource
) {
    suspend fun searchIcons(query: String) = remoteDataSource.searchIcons(query)
   fun getCategories() = localDataSource.getCategories()


    suspend fun insertCategories(categories: List<Category>) = localDataSource.insert(categories)

}