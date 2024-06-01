package cz.cvut.fit.cervem27.tasks.features.category.data.db

import androidx.compose.ui.graphics.Color
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "categories")
data class DbCategory(
    @PrimaryKey(autoGenerate = true) val categoryId: Long,
    val categoryName: String,
    val iconUrl: String?,
    val iconHue: Float
)
