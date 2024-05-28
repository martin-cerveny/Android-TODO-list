package cz.cvut.fit.cervem27.tasks.features.task.presentation.listTasks

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cz.cvut.fit.cervem27.tasks.features.task.data.TaskRepository
import cz.cvut.fit.cervem27.tasks.features.task.domain.Task
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Calendar
import java.util.Date

class TasksListViewModel(
    private val taskRepository: TaskRepository
) : ViewModel() {

    private val _stateStream = MutableStateFlow(TaskListScreenState())
    val stateStream = _stateStream.asStateFlow()

    private val calendar = Calendar.getInstance().apply {
        set(Calendar.HOUR_OF_DAY, 0)
        set(Calendar.MINUTE, 0)
        set(Calendar.SECOND, 0)
        set(Calendar.MILLISECOND, 0)
    }
    val today = calendar.timeInMillis

    init {
        viewModelScope.launch {
            taskRepository.getTasks().collect{ tasks ->
                _stateStream.update { it.copy(tasks = tasks) }
            }
        }
    }
}
data class TaskListScreenState(
    val tasks: List<Task> = emptyList()
)