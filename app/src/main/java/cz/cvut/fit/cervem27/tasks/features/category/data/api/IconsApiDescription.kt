package cz.cvut.fit.cervem27.tasks.features.category.data.api

import retrofit2.http.GET
import retrofit2.http.Query

interface IconsApiDescription {
    @GET("search")
    suspend fun searchIcons(@Query("query") query: String): IconsResponse
}