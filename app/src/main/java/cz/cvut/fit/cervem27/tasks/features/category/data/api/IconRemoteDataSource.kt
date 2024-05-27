package cz.cvut.fit.cervem27.tasks.features.category.data.api

import android.util.Log
import androidx.compose.ui.graphics.Color
import cz.cvut.fit.cervem27.tasks.core.ApiConstants
import cz.cvut.fit.cervem27.tasks.features.category.domain.Icon

class IconRemoteDataSource (private val apiDescription: IconsApiDescription){
    suspend fun searchIcons(query: String): List<Icon>{
        return apiDescription.searchIcons(query).icons.map { icon ->
            Log.d("icons:", icon)
            Icon(
                url = ApiConstants.BASE_URL + icon + ".svg",
                color = Color.Transparent
            )
        }
    }
}