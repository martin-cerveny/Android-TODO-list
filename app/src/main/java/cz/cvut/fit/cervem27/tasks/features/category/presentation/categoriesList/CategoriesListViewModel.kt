package cz.cvut.fit.cervem27.tasks.features.category.presentation.categoriesList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.logEvent
import cz.cvut.fit.cervem27.tasks.features.category.data.CategoryRepository
import cz.cvut.fit.cervem27.tasks.features.category.domain.Category
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CategoriesListViewModel(
    private val categoryRepository: CategoryRepository,
    private val firebaseAnalytics: FirebaseAnalytics
) : ViewModel() {
    private val _screenStateStream =  MutableStateFlow(CategoryListScreenState())
    val screenStateStream get() = _screenStateStream.asStateFlow()

    init {
        viewModelScope.launch {
            categoryRepository.getCategories().collect{ categories ->
                _screenStateStream.update { it.copy(categories = categories) }
            }
        }
    }
    fun onDeleteConfirmation() {
        screenStateStream.value.categoryToBeDeleted?.let{ category ->
            viewModelScope.launch {
                categoryRepository.deleteCategory(category)
            }
            _screenStateStream.update { it.copy(categoryToBeDeleted = null) }
        }
    }

    fun onDeleteCancellation(){
        _screenStateStream.update { it.copy(categoryToBeDeleted = null) }
    }

    fun onTryToDeleteCategory(category: Category){
        _screenStateStream.update { it.copy(categoryToBeDeleted = category) }
    }

    fun onCategoryEdit(){
        firebaseAnalytics.logEvent("edit_category"){
            param("param", "value")
        }
    }

}


data class CategoryListScreenState(
    val categories: List<Category> = emptyList(),
    val categoryToBeDeleted: Category? = null
)
