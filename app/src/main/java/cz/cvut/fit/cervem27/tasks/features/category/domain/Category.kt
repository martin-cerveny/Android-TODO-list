package cz.cvut.fit.cervem27.tasks.features.category.domain

import androidx.compose.ui.graphics.Color

data class Category (
    val categoryId: Long,
    val categoryName: String,
    val url: Url?,
    val colorHue: Float
)
