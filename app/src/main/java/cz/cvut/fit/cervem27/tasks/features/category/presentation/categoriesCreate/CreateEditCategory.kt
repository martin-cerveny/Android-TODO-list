package cz.cvut.fit.cervem27.tasks.features.category.presentation.categoriesCreate

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
                            viewModel.onCreateCategory()
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
                categoryName = screenState.categoryName,
                categoryIcon = screenState.selectedCategoryIcon,
                onCategoryNameChange = viewModel::onCategoryNameChange
            )
            Spacer(modifier = Modifier.height(20.dp))
            ColorsPicker(screenState.colors, screenState.selectedColorIndex, viewModel::onClickColor)
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
            MySvgImage(
                url = icon.url,
                modifier = Modifier
                    .padding(8.dp)
                    .size(50.dp)
                    .clickable(
                        onClick = {
                            onIconSelect(icon.url)
                        }
                    )
                ,
                tint = MaterialTheme.colorScheme.onBackground
            )
        }
    }

}

@Composable
fun ColorsPicker(
    colors: List<Color>,
    selected: Int,
    onClickColor: (Int) -> Unit
){
    val circleSize = 40.dp

    Row (
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ){
        for ((index, color) in colors.withIndex()) {

           Box(modifier = if(selected == index) Modifier
               .size(circleSize)
               .background(color = color.copy(alpha = 0.5f), shape = CircleShape)
                 else Modifier.size(circleSize)
           ){
               Box(
                   modifier = Modifier
                       .padding(4.dp)
                       .clip(CircleShape)
                       .fillMaxSize()
                       .clickable(onClick = { onClickColor(index) })
                       .background(color = color)

               )
           }
        }
    }
}

@Composable
fun Header(
    categoryName: String,
    categoryIcon: CategoryIcon,
    onCategoryNameChange: (String) -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
    ) {
        CategoryImage(
            categoryIcon
        )
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
            .background(color = categoryIcon.color, shape = RoundedCornerShape(14.dp))

    ) {
        MySvgImage(
            url = categoryIcon.url,
            modifier = Modifier
                .padding(8.dp)
                .fillMaxSize(),
            tint = tint
        )

    }
}