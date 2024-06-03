package cz.cvut.fit.cervem27.tasks.features.category.presentation.categoriesCreate



import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cz.cvut.fit.cervem27.tasks.core.Screen
import cz.cvut.fit.cervem27.tasks.features.category.data.CategoryRepository
import cz.cvut.fit.cervem27.tasks.features.category.domain.Category
import cz.cvut.fit.cervem27.tasks.features.category.domain.Url
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@OptIn(FlowPreview::class)
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
            id?.let { categoryId ->         // id argument passed -> loading an existing category
                val category = createRepository.getCategory(categoryId)
                _categoryStateStream.update {
                    it.copy(
                        categoryName = category.categoryName,
                        iconUrl = category.url,
                        iconHue = category.colorHue
                    )
                }
            }
        }


        viewModelScope.launch {
            _categoryStateStream
                .map { it.iconQuery }
                .debounce(500)
                .distinctUntilChanged()
                .collectLatest { query ->
                    if (query.length >= 3) {
                        try {
                            val icons = createRepository.searchIcons(query)
                            _categoryStateStream.update {
                                it.copy(iconsResult = icons,
                                    searchState = ScreenState.SearchState.OK
                                )
                            }
                        } catch (t: Throwable){
                            _categoryStateStream.update {
                                it.copy(iconsResult = emptyList(),
                                    searchState = ScreenState.SearchState.ERROR
                                )
                            }
                        }
                    } else {
                        _categoryStateStream.update {
                            it.copy(
                                iconsResult = emptyList(),
                                searchState = ScreenState.SearchState.OK
                            )
                        }
                    }
                }
        }

    }

    fun onIconsSearchQueryChange(query: String){
        _categoryStateStream.update { it.copy(
            iconQuery = query,
            searchState = ScreenState.SearchState.SEARCHING
        ) }
    }

    fun onCategoryNameChange(name: String){
        _categoryStateStream.update {
            it.copy(categoryName = name)
        }
    }

    fun onColorChange(hue: Float){
        _categoryStateStream.update {
            it.copy(iconHue = hue)
        }
    }

    fun onConfirm(){
        viewModelScope.launch {
            val category = Category(
                categoryId = id?:0,
                categoryName = categoryStateStream.value.categoryName,
                url = categoryStateStream.value.iconUrl,
                colorHue =  categoryStateStream.value.iconHue
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
           screenState.copy(iconUrl = iconUrl )
        }
    }
    fun clearCategoryName(){
        _categoryStateStream.update {
            it.copy(categoryName = "")
        }
    }

    fun clearIconsSearchQuery(){
        _categoryStateStream.update {
            it.copy(
                iconQuery = "",
                iconsResult = emptyList()
            )
        }
    }
}

data class ScreenState(
    val categoryName: String = "",
    val iconUrl: String? = null,
    val iconHue: Float = 0f, // todo
    val iconQuery: String = "",
    val iconsResult: List<Url> = emptyList(),
    val searchState: SearchState = SearchState.OK
) {
    enum class SearchState{
        SEARCHING,
        OK,
        ERROR
    }
}