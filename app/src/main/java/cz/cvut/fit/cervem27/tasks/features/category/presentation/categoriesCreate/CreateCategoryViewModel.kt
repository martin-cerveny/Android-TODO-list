package cz.cvut.fit.cervem27.tasks.features.category.presentation.categoriesCreate


import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cz.cvut.fit.cervem27.tasks.features.category.data.CategoryRepository
import cz.cvut.fit.cervem27.tasks.features.category.domain.Category
import cz.cvut.fit.cervem27.tasks.features.category.domain.Icon
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CreateCategoryViewModel(
    private val createRepository: CategoryRepository,
) : ViewModel() {
    private val _categoryStateStream = MutableStateFlow(ScreenState())
    val categoryStateStream = _categoryStateStream.asStateFlow()



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
            it.copy(categoryName = name)
        }
    }

    fun onClickColor(colorIndex: Int){

        _categoryStateStream.update {
            it.copy(selectedColorIndex = colorIndex)
        }
        _categoryStateStream.update {
            it.copy(selectedIcon = Icon(url = it.selectedIcon.url,color = it.selectedColor() ))
        }

    }

    fun onCreateCategory(){
        viewModelScope.launch {
            createRepository.insertCategories(listOf(
                Category(
                    categoryId = 0,
                    categoryName = categoryStateStream.value.categoryName,
                    icon = categoryStateStream.value.selectedIcon,
                )
            ))
        }

    }

    fun onIconChange(iconUrl: String){
        _categoryStateStream.update {
            it.copy(selectedIcon = Icon(url = iconUrl, color = it.selectedColor()))
        }
    }
    fun clearCategoryName(){
        _categoryStateStream.update {
            it.copy(categoryName = "")
        }
    }

    fun clearIconsSearchQuery(){
        _categoryStateStream.update {
            it.copy(iconQuery = "", iconsResult = emptyList())
        }
    }
}

data class ScreenState(
    val categoryName: String = "",
    val colors: List<Color> = listOf(
        Color(0xFFFD9854),
        Color(0xFFD15E5E),
        Color(0xFFD15EBB),
        Color(0xFF86DC86),
        Color(0xFF4DA8CB),
        Color(0xFFE1D5F2),
        ),
    val selectedColorIndex: Int  = 0,
    val selectedIcon: Icon = Icon("https://api.iconify.design/mdi:injection-off.svg", Color(0xFFFD9854)),
    val iconQuery: String = "",
    val iconsResult: List<Icon> = emptyList()
){
    fun selectedColor() = colors[selectedColorIndex]
}