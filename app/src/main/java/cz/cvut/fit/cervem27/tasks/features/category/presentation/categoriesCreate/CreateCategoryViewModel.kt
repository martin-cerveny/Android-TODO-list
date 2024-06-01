package cz.cvut.fit.cervem27.tasks.features.category.presentation.categoriesCreate


import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cz.cvut.fit.cervem27.tasks.core.Screen
import cz.cvut.fit.cervem27.tasks.features.category.data.CategoryRepository
import cz.cvut.fit.cervem27.tasks.features.category.domain.Category
import cz.cvut.fit.cervem27.tasks.features.category.domain.CategoryIcon
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CreateCategoryViewModel(
    private val createRepository: CategoryRepository,
    private val savedStateHandle: SavedStateHandle,
) : ViewModel() {
    private val id: Long?
        get() = savedStateHandle[Screen.CategoriesEditScreen.ID_KEY]

    private val _categoryStateStream = MutableStateFlow(ScreenState())
    val categoryStateStream = _categoryStateStream.asStateFlow()

    init {
        viewModelScope.launch {
            id?.let { categoryId ->
                val category = createRepository.getCategory(categoryId)
                _categoryStateStream.update {
                    it.copy(
                        name = category.categoryName,
                        categoryUrl = category.categoryIcon.url,
                        selectedHue = category.categoryIcon.colorHue
                    )
                }
            }
        }
    }






    fun onIconsSearchQueryChange(query: String){



        _categoryStateStream.update { it.copy(iconQuery = query) }
        if(query.length < 3){
            // todo enter at least 3 characters
        } else {
            viewModelScope.launch {
                try {
                  //  createRepository.getCategories()
                    _categoryStateStream.update {
                        it.copy(iconsResult = createRepository.searchIcons(query))
                    }
                } catch (t: Throwable){
                    _categoryStateStream.update { it.copy(iconsResult =  emptyList())}
                    //todo network error notice user
                }
            }
        }
    }

    fun onCategoryNameChange(name: String){
        _categoryStateStream.update {
            it.copy(name = name)
        }
    }

    fun onColorChange(hue: Float){
        _categoryStateStream.update {
            it.copy(selectedHue = hue)
        }
    }

    fun onConfirm(){
        viewModelScope.launch {
            val category = Category(
                categoryId = id?:0,
                categoryName = categoryStateStream.value.name,
                categoryIcon = CategoryIcon(
                    url = categoryStateStream.value.categoryUrl?:"",
                    colorHue =  categoryStateStream.value.selectedHue
                ),
            )
            id?.let {
                createRepository.updateCategory(category)
            }?:run {
                createRepository.insertCategory(category)
            }
        }
    }

    fun onIconChange(iconUrl: String){
        _categoryStateStream.update { screenState ->
           screenState.copy(categoryUrl = iconUrl )
        }
    }
    fun clearCategoryName(){
        _categoryStateStream.update {
            it.copy(name = "")
        }
    }

    fun clearIconsSearchQuery(){
        _categoryStateStream.update {
            it.copy(iconQuery = "", iconsResult = emptyList())
        }
    }
}

data class ScreenState(
    val name: String = "",
    val categoryUrl: String? = null,
    val selectedHue: Float = 0f,
    val iconQuery: String = "",
    val iconsResult: List<CategoryIcon> = emptyList()
)