package cz.cvut.fit.cervem27.tasks.features.category.presentation.categoriesList

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cz.cvut.fit.cervem27.tasks.features.category.data.CategoryRepository
import cz.cvut.fit.cervem27.tasks.features.category.domain.Category
import cz.cvut.fit.cervem27.tasks.features.task.domain.Task
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CategoriesListViewModel(
    private val categoryRepository: CategoryRepository,
) : ViewModel() {
    private val _screenStateStream =  MutableStateFlow(CategoryListScreenState())

    val screenStateStream get() = _screenStateStream.asStateFlow()

    fun onDeleteConfirmation() {
        screenStateStream.value.categoryToBeDeleted?.let{ category ->
            viewModelScope.launch {
                categoryRepository.deleteCategory(category)
            }
            _screenStateStream.update { it.copy(categoryToBeDeleted = null) }
        }
    }

    fun onDeleteCancelation(){
        _screenStateStream.update { it.copy(categoryToBeDeleted = null) }
    }

    fun onTryToDeleteCategory(category: Category){
        _screenStateStream.update { it.copy(categoryToBeDeleted = category) }
    }




    init {
        viewModelScope.launch {
            categoryRepository.getCategories().collect{ categories ->
                _screenStateStream.update { it.copy(categories = categories) }
            }
        }
    }
}


data class CategoryListScreenState(
    val categories: List<Category> = emptyList(),
    val categoryToBeDeleted: Category? = null
)
