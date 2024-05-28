package cz.cvut.fit.cervem27.tasks.features.task.presentation.createTask

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cz.cvut.fit.cervem27.tasks.features.category.data.CategoryRepository
import cz.cvut.fit.cervem27.tasks.features.category.data.db.CategoryLocalDataSource
import cz.cvut.fit.cervem27.tasks.features.category.domain.Category
import cz.cvut.fit.cervem27.tasks.features.category.domain.CategoryIcon
import cz.cvut.fit.cervem27.tasks.features.task.data.TaskRepository
import cz.cvut.fit.cervem27.tasks.features.task.domain.Task
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Calendar
import java.util.Date

class CreateTaskViewModel(
    private val taskRepository: TaskRepository,
    private val categoryRepository: CategoryRepository
) : ViewModel() {

    private val _stateStream = MutableStateFlow(CreateTaskScreenState())
    val stateStream = _stateStream.asStateFlow()

    private val calendar = Calendar.getInstance().apply {
        set(Calendar.HOUR_OF_DAY, 0)
        set(Calendar.MINUTE, 0)
        set(Calendar.SECOND, 0)
        set(Calendar.MILLISECOND, 0)
    }
    init {
        viewModelScope.launch {
            categoryRepository.getCategories().collect{ categories ->
                _stateStream.update { it.copy(categories = categories) }
            }
        }
    }

    fun onTaskNameChange(name: String){
        _stateStream.update {
            it.copy(taskName = name)
        }

    }

    fun dateValidator(time: Long) : Boolean{

        val today: Long = calendar.timeInMillis

        return time >= today
    }

    fun addTask() {//task: Task){
        stateStream.value.category?.let {category ->
            viewModelScope.launch {
                taskRepository.insertTask(
                    Task(
                        0,
                        stateStream.value.taskName,
                        category = category,
                        Date(242423),
                        emptyList()
                    )
                )
            }
        }
    }



    fun onSelectedCategory(category: Category) {
        _stateStream.update {
            it.copy(
                categoriesSelectExpanded = false,
                category = category
            )
        }
    }

    fun onSelectCategory(){
        _stateStream.update {
            it.copy( categoriesSelectExpanded = true)
        }
    }

    fun onSelectDate(date: Date?){
        _stateStream.update {
            it.copy(
                date = date,
                dateSelectExpanded = false
            )
        }
    }

    fun onShowCalendar(){
        _stateStream.update {
            it.copy( dateSelectExpanded = true)
        }
    }


}
data class CreateTaskScreenState(
    val taskName: String = "",
    val category: Category? = null,
    val categoriesSelectExpanded: Boolean = false,
    val date: Date? = null,
    val dateSelectExpanded: Boolean = false,
    val categories: List<Category> = emptyList(),
    val subtasks: List<Task.Subtask> = emptyList(),


)