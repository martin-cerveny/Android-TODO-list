package cz.cvut.fit.cervem27.tasks.features.task.data.db

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import cz.cvut.fit.cervem27.tasks.features.category.data.db.DbCategory

@Entity(
    tableName = "tasks",
    foreignKeys = [ForeignKey(
        entity = DbCategory::class,
        parentColumns = ["categoryId"],
        childColumns = ["categoryId"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class DbTask(
    @PrimaryKey(autoGenerate = true) val taskId: Long,
    val categoryId: Long,
    val taskName: String,
    // add date
)