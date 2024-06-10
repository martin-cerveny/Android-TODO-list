package cz.cvut.fit.cervem27.tasks.features.category.data.api

import android.util.Log
import cz.cvut.fit.cervem27.tasks.features.category.domain.Url

class IconRemoteDataSource (private val apiDescription: IconsApiDescription){
    suspend fun searchIcons(query: String): List<Url>{
        return apiDescription.searchIcons(query).icons.map { icon ->
                // API returns icon names only
                // svg image url is created by appending icon name (followed by '.svg')
                // to BASE_URL
                ApiConstants.BASE_URL + icon + ".svg"
        }
    }
}