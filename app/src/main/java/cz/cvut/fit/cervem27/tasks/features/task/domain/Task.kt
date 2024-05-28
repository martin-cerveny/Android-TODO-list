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

val tasks = listOf(
    Task(1,"Semestral project", Category(1, "BI-AND", CategoryIcon("https://api.iconify.design/mdi:injection-off.svg", Color(0xFFFD9854))),Date(242423), listOf(Task.Subtask(1,"ui compose", true), Task.Subtask(2,"DB room", false), Task.Subtask(3, "API iconify", false))),
    Task(2, "Fakebook", Category(1, "BI-BEK", CategoryIcon("https://api.iconify.design/mdi:injection-off.svg", Color(0xFFD15E5E))), Date(53525), listOf(Task.Subtask(4, "Steal cookie", false)))
)