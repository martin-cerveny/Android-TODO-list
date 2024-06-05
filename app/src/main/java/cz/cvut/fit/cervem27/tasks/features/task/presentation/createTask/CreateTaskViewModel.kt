package cz.cvut.fit.cervem27.tasks.features.task.presentation.createTask


import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cz.cvut.fit.cervem27.tasks.core.Screen
import cz.cvut.fit.cervem27.tasks.features.category.data.CategoryRepository
import cz.cvut.fit.cervem27.tasks.features.category.domain.Category
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
    private val categoryRepository: CategoryRepository,
    private val savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val id: Long?
        get() = savedStateHandle[Screen.TasksEditScreen.ID_KEY]

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
            id?.let { taskId ->     // editing an exiting task -> get task from db
                val task = taskRepository.getTask(taskId)
                _stateStream.update {
                    it.copy(
                        taskName = task.name,
                        category = task.category,
                        date = task.date
                    )
                }
            }
        }
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

    fun addTask() {
        viewModelScope.launch {
            val task = Task(
                taskId = id?:0,
                name = stateStream.value.taskName,
                category = stateStream.value.category,
                date = stateStream.value.date,
            )
            id?.let {
                taskRepository.updateTask(task)
            }?:run {
                taskRepository.insertTask(task)
            }
        }
    }



    fun onSelectedCategory(category: Category?) {
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
)