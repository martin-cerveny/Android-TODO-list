package cz.cvut.fit.cervem27.tasks.features.category.data.api

import android.util.Log
import androidx.compose.ui.graphics.Color
import cz.cvut.fit.cervem27.tasks.core.ApiConstants
import cz.cvut.fit.cervem27.tasks.features.category.domain.CategoryIcon

class IconRemoteDataSource (private val apiDescription: IconsApiDescription){
    suspend fun searchIcons(query: String): List<CategoryIcon>{
        return apiDescription.searchIcons(query).icons.map { icon ->
            Log.d("icons:", icon)
            CategoryIcon(
                url = ApiConstants.BASE_URL + icon + ".svg",
                colorHue = 0f
            )
        }
    }
}