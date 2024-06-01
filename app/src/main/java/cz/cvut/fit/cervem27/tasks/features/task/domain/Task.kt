package cz.cvut.fit.cervem27.tasks.features.task.domain

import androidx.compose.ui.graphics.Color
import cz.cvut.fit.cervem27.tasks.features.category.domain.Category
import cz.cvut.fit.cervem27.tasks.features.category.domain.CategoryIcon
import java.util.Date


data class Task (
    val taskId: Long,
    val name: String,
    val category: Category,
    val date: Date?,
    val subtasks: List<Subtask>

){
    data class Subtask(
        val subtaskId: Long,
        val title: String,
        val completed: Boolean,
    )
}
