package cz.cvut.fit.cervem27.tasks.features.category.domain

import androidx.compose.ui.graphics.Color

data class Category (
    val categoryId: Long,
    val categoryName: String,
    val categoryIcon: CategoryIcon
)

val categories = listOf(
    Category(1,"AND", CategoryIcon("https://api.iconify.design/mdi:injection-off.svg", Color(0xFFD15E5E))),
    Category(1,"AND", CategoryIcon("https://api.iconify.design/mdi:injection-off.svg", Color(0xFFD15E5E))),
    Category(1,"AND", CategoryIcon("https://api.iconify.design/mdi:injection-off.svg", Color(0xFFD15E5E))),
    Category(1,"AND", CategoryIcon("https://api.iconify.design/mdi:injection-off.svg", Color(0xFFD15E5E))),
    Category(1,"AND", CategoryIcon("https://api.iconify.design/mdi:injection-off.svg", Color(0xFFD15E5E))),
    Category(1,"AND", CategoryIcon("https://api.iconify.design/mdi:injection-off.svg", Color(0xFFD15E5E))),
    Category(1,"AND", CategoryIcon("https://api.iconify.design/mdi:injection-off.svg", Color(0xFFD15E5E))),
    Category(1,"AND", CategoryIcon("https://api.iconify.design/mdi:injection-off.svg", Color(0xFFD15E5E))),
    Category(1,"AND", CategoryIcon("https://api.iconify.design/mdi:injection-off.svg", Color(0xFFD15E5E))),
    Category(1,"AND", CategoryIcon("https://api.iconify.design/mdi:injection-off.svg", Color(0xFFD15E5E))),
    Category(1,"AND", CategoryIcon("https://api.iconify.design/mdi:injection-off.svg", Color(0xFFD15E5E))),
    Category(1,"AND", CategoryIcon("https://api.iconify.design/mdi:injection-off.svg", Color(0xFFD15E5E))),
    Category(1,"AND", CategoryIcon("https://api.iconify.design/mdi:injection-off.svg", Color(0xFFD15E5E))),

    )