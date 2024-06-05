package cz.cvut.fit.cervem27.tasks.features.category.domain

data class Category (
    val categoryId: Long,
    val categoryName: String,
    val iconUrl: Url?,
    val colorHue: Float
)
