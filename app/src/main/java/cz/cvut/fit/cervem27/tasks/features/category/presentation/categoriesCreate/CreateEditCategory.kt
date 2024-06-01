package cz.cvut.fit.cervem27.tasks.features.category.presentation.categoriesCreate

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import cz.cvut.fit.cervem27.tasks.R
import org.koin.androidx.compose.koinViewModel
import cz.cvut.fit.cervem27.tasks.features.category.domain.CategoryIcon
import cz.cvut.fit.cervem27.tasks.features.category.presentation.categoriesList.MySvgImage

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateEditCategory(
    navController: NavController,
    viewModel: CreateCategoryViewModel = koinViewModel(),
){
    val screenState by viewModel.categoryStateStream.collectAsStateWithLifecycle()
    Scaffold(
        topBar = {
            TopAppBar(
                title = {},
                actions = {
                    Button(
                        onClick = {
                            viewModel.onConfirm()
                            navController.navigateUp()
                          },
                        modifier = Modifier
                            .padding(8.dp)

                    ) {
                        Text(text = "Create")
                    }
                },

                navigationIcon = {

                    Icon(
                        imageVector = Icons.Default.Close,
                        modifier = Modifier
                            .padding(8.dp)
                            .size(30.dp)
                            .clip(CircleShape)
                            .background(Color.Gray)
                            .clickable(
                                onClick = { navController.navigateUp() }
                            )
                        ,
                        tint = MaterialTheme.colorScheme.background,
                        contentDescription = null,


                    )


                },


            )
        },
        bottomBar = { Spacer(modifier = Modifier.height(0.dp))},
    ){
        Column(modifier = Modifier
            .padding(it)
            .padding(8.dp)) {
            Header(
                categoryName = screenState.name,
                imageUrl = screenState.categoryUrl,
                colorHue = screenState.selectedHue,
                onCategoryNameChange = viewModel::onCategoryNameChange
            )
            Spacer(modifier = Modifier.height(20.dp))
            ColorsPicker(
                selectedHue = screenState.selectedHue,
                viewModel::onColorChange
            )
            Spacer(modifier = Modifier.height(20.dp))
            SearchIconHeader(
                screenState.iconQuery,
                viewModel::onIconsSearchQueryChange
            )
            Spacer(modifier = Modifier.height(20.dp))
            SearchIconResults(
                screenState.iconsResult,
                modifier = Modifier.weight(1f),
                onIconSelect = viewModel::onIconChange
            )
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchIconHeader(
    iconSearchQuery: String,
    onIconSearchQueryChange: (String) -> Unit
){
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.height(70.dp)
    ) {



        TextField(
            value = iconSearchQuery,
            onValueChange = {
                onIconSearchQueryChange(it)
            },
            leadingIcon = {Icon(imageVector = Icons.Default.Search, contentDescription = null)},
            placeholder = { Text(text = stringResource(R.string.search_icon))},
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                disabledContainerColor = Color.Transparent,
            ),
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

    }

}

@Composable
fun SearchIconResults(
    categoryIcons: List<CategoryIcon>,
    modifier: Modifier = Modifier,
    onIconSelect: (String) -> Unit
){
//    val icons: List<ImageVector> = listOf(Icons.Default.AccountCircle,Icons.Default.AccountCircle,Icons.Default.AccountCircle,Icons.Default.AccountCircle,Icons.Default.AccountCircle,Icons.Default.AccountCircle,Icons.Default.AccountCircle,Icons.Default.AccountCircle,Icons.Default.AccountCircle,Icons.Default.AccountCircle,Icons.Default.AccountCircle,Icons.Default.AccountCircle,Icons.Default.AccountCircle,Icons.Default.AccountCircle,Icons.Default.AccountCircle,Icons.Default.AccountCircle,Icons.Default.AccountCircle,Icons.Default.AccountCircle,Icons.Default.AccountCircle,Icons.Default.AccountCircle,Icons.Default.AccountCircle,Icons.Default.AccountCircle,Icons.Default.AccountCircle,Icons.Default.AccountCircle,Icons.Default.AccountCircle,Icons.Default.AccountCircle,Icons.Default.AccountCircle,Icons.Default.AccountCircle,Icons.Default.AccountCircle,Icons.Default.AccountCircle,)

    LazyVerticalGrid(
        modifier = modifier,
        columns = GridCells.Fixed(6),
    ) {
        items(categoryIcons){ icon ->
            icon.url?.let {url ->
                MySvgImage(
                    url = url,
                    modifier = Modifier
                        .padding(8.dp)
                        .size(50.dp)
                        .clickable(
                            onClick = {
                                onIconSelect(url)
                            }
                        ),
                    tint = MaterialTheme.colorScheme.onBackground
                )
            }
        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ColorsPicker(
    selectedHue: Float,
    onColorChange: (Float) -> Unit
){
    val circleSize = 40.dp

    val color = Constants.hslColor(hue = selectedHue)

    Slider(
        value = selectedHue,
        onValueChange = onColorChange,
        valueRange = 0f..360f,
        modifier = Modifier.padding(vertical = 8.dp),
        colors = SliderDefaults.colors(
            thumbColor = color,
            activeTrackColor =  Color.Gray,
            inactiveTrackColor =  Color.Gray,
        ),
        thumb = {
            Box(
                modifier = Modifier
                    .size(30.dp)
                    .background(color = color, shape = CircleShape)
            )
        },
    )

}

@Composable
fun Header(
    categoryName: String,
    imageUrl: String?,
    colorHue: Float,
    onCategoryNameChange: (String) -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
    ) {
        imageUrl?.let { url ->
            CategoryImage(
                CategoryIcon(url = url, colorHue = colorHue)
            )
        }?:run{
//            TODO("ICON IMAGE IS NULL")
        }
        Spacer(modifier = Modifier.width(10.dp))
        OutlinedTextField(
            value = categoryName,
            label = { Text(stringResource(R.string.category_name)) },
            onValueChange = {onCategoryNameChange(it)},
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier
                .fillMaxWidth(),
            singleLine = true
        )
    }

}


@Composable
fun CategoryImage(
    categoryIcon: CategoryIcon,
    tint: Color = Color.Black
){
    Box(
        modifier = Modifier
            .padding(8.dp)
            .size(50.dp)
            .background(color = Constants.hslColor(categoryIcon.colorHue), shape = RoundedCornerShape(14.dp))

    ) {

        MySvgImage(
            url = categoryIcon.url?:"",
            modifier = Modifier
                .padding(8.dp)
                .fillMaxSize(),
            tint = Color.Black
        )

    }
}



object Constants {
    const val DEFAULT_SATURATION = 0.7f // 100%
    const val DEFAULT_LIGHTNESS = 0.5f  // 50%

    fun hslColor(hue: Float): Color{
        return Color.hsl(
            hue = hue,
            saturation = DEFAULT_SATURATION,
            lightness = DEFAULT_LIGHTNESS
        )
    }
}