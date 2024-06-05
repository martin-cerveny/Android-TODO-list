package cz.cvut.fit.cervem27.tasks.features.task.data.db

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import cz.cvut.fit.cervem27.tasks.features.category.data.db.DbCategory
import java.util.Date

@Entity(
    tableName = "tasks",
    foreignKeys = [ForeignKey(
        entity = DbCategory::class,
        parentColumns = ["categoryId"],
        childColumns = ["categoryId"],
        onDelete = ForeignKey.CASCADE
    )],
    indices = [Index(value = ["categoryId"])]
)
data class DbTask(
    @PrimaryKey(autoGenerate = true) val taskId: Long,
    val categoryId: Long?,
    val taskName: String,
    val deadline: Date?
)


