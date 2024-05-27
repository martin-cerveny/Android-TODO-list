package cz.cvut.fit.cervem27.tasks.features.category.data

import cz.cvut.fit.cervem27.tasks.features.category.data.api.IconRemoteDataSource

class CategoryRepository(
    private val remoteDataSource: IconRemoteDataSource
) {
    suspend fun searchIcons(query: String) = remoteDataSource.searchIcons(query)
}