package cz.cvut.fit.cervem27.tasks.features.task.data.db

import androidx.room.Embedded
import androidx.room.Relation
import cz.cvut.fit.cervem27.tasks.features.category.data.db.DbCategory

data class DbTaskWithCategory(
    @Embedded val task: DbTask,
    @Relation(
        parentColumn = "categoryId",
        entityColumn = "categoryId"
    )
    val category: DbCategory?
)