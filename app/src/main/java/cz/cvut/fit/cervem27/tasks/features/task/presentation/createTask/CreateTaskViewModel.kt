package cz.cvut.fit.cervem27.tasks.features.task.presentation.createTask

import android.util.Log
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cz.cvut.fit.cervem27.tasks.features.category.data.CategoryRepository
import cz.cvut.fit.cervem27.tasks.features.category.domain.Category
import cz.cvut.fit.cervem27.tasks.features.category.domain.Icon
import cz.cvut.fit.cervem27.tasks.features.category.presentation.categoriesCreate.ScreenState
import cz.cvut.fit.cervem27.tasks.features.task.data.TaskRepository
import cz.cvut.fit.cervem27.tasks.features.task.domain.Task
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Date

class CreateTaskViewModel(
    private val taskRepository: TaskRepository
) : ViewModel() {

    private val _stateStream = MutableStateFlow(CreateTaskScreenState())
    val stateStream = _stateStream.asStateFlow()

    fun onTaskNameChange(name: String){
        _stateStream.update {
            it.copy(taskName = name)
        }

    }


    fun addTask() {//task: Task){
        viewModelScope.launch {
            taskRepository.insertTask(
                Task(0,
                    stateStream.value.taskName,
                    Category(1, "BI-AND", Icon("https://api.iconify.design/mdi:injection-off.svg", Color(0xFFFD9854))),
                    Date(242423),
                    listOf(Task.Subtask(1, "ui compose", true), Task.Subtask(2, "DB room", false), Task.Subtask(3, "API iconify", false)))
            )
        }
    }


    fun onSelectedCategory(item: String) {
        _stateStream.update {
            it.copy(
                categoriesSelectExpanded = false,
                category = item
            )
        }
    }

    fun onSelectCategory(){
        _stateStream.update {
            it.copy( categoriesSelectExpanded = true)
        }
    }

    fun OnShowCalendar(){
        _stateStream.update {
            it.copy( showCalendar = true)
        }
    }
    fun hideCalendar(){
        _stateStream.update {
            it.copy( showCalendar = false)
        }
    }

}
data class CreateTaskScreenState(
    val taskName: String = "",
    val category: String? = "Vyberte kategorii",
    val date: Date? = null,
    val subtasks: List<Task.Subtask> = emptyList(),
    val categoriesSelectExpanded: Boolean = false,
    val showCalendar:Boolean = true

)