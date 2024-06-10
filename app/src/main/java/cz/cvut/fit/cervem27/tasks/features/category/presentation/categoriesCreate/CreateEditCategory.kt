package cz.cvut.fit.cervem27.tasks.features.category.presentation.categoriesCreate

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import cz.cvut.fit.cervem27.tasks.R
import cz.cvut.fit.cervem27.tasks.core.ui.theme.IconColorsConstants
import cz.cvut.fit.cervem27.tasks.features.category.domain.Url
import org.koin.androidx.compose.koinViewModel
import cz.cvut.fit.cervem27.tasks.features.category.presentation.CategoryIconColoredBackground
import cz.cvut.fit.cervem27.tasks.features.category.presentation.SvgImage

@Composable
fun CreateEditCategory(
    navController: NavController,
    viewModel: CreateCategoryViewModel = koinViewModel(),
){
    val screenState by viewModel.categoryStateStream.collectAsStateWithLifecycle()


    Column(modifier = Modifier.padding(8.dp)) {
        ConfirmButtons(
            onConfirm = {
                viewModel.onConfirm(then = {navController.navigateUp()})

            },
            onCancel = {
                navController.navigateUp()
            },
        )

        Header(
            categoryName = screenState.categoryName,
            iconUrl = screenState.iconUrl,
            iconColor = IconColorsConstants.hslColor(screenState.iconHue),
            onCategoryNameChange = viewModel::onCategoryNameChange
        )

        Spacer(modifier = Modifier.height(20.dp))

        ColorsPicker(
            selectedHue = screenState.iconHue,
            viewModel::onColorChange
        )

        Spacer(modifier = Modifier.height(20.dp))

        SearchIconInputField(
            screenState.iconQuery,
            viewModel::onIconsSearchQueryChange
        )

        Spacer(modifier = Modifier.height(20.dp))

        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            contentAlignment = Alignment.TopCenter
        ){

            when(screenState.searchState){
                ScreenState.SearchState.SEARCHING -> LoadingIcons()
                ScreenState.SearchState.ERROR -> IconsSearchingError()
                ScreenState.SearchState.OK ->
                    if(screenState.iconsResult.isEmpty()){
                        NoIconsFound(query = screenState.iconQuery)
                    } else {
                        SearchIconResults(
                            categoryIcons = screenState.iconsResult,
                            onIconSelect = viewModel::onIconChange
                        )
                    }
            }
        }
    }
}

@Composable
fun ConfirmButtons(
    onConfirm: () -> Unit,
    onCancel: () -> Unit
){
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {

        TextButton(
            onClick = onCancel,
            colors = ButtonDefaults.textButtonColors(
                contentColor = MaterialTheme.colorScheme.tertiary
            )
        ) {
            Text(text = stringResource(R.string.cancel))
        }

        TextButton(
            onClick = onConfirm,
        ) {
            Text(text = stringResource(R.string.confirm))
        }


    }
}


@Composable
fun SearchIconInputField(
    iconSearchQuery: String,
    onIconSearchQueryChange: (String) -> Unit
){
    TextField(
        value = iconSearchQuery,
        onValueChange = {onIconSearchQueryChange(it)},
        leadingIcon = {Icon(imageVector = Icons.Default.Search, contentDescription = stringResource(R.string.search_icon))},
        placeholder = { Text(text = stringResource(R.string.search_icon))},
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color.Transparent,
            unfocusedContainerColor = Color.Transparent,
            disabledContainerColor = Color.Transparent,
            focusedTextColor = MaterialTheme.colorScheme.onBackground,
            focusedLeadingIconColor = MaterialTheme.colorScheme.onBackground,
            focusedPlaceholderColor = MaterialTheme.colorScheme.onBackground,
            unfocusedPlaceholderColor = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.8f),
            unfocusedTextColor = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.8f),
            unfocusedLeadingIconColor = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.8f)
        ),
        modifier = Modifier.fillMaxWidth(),
        singleLine = true
    )

}

@Composable
fun SearchIconResults(
    categoryIcons: List<Url>,
    modifier: Modifier = Modifier,
    onIconSelect: (String) -> Unit
){
    LazyVerticalGrid(
        modifier = modifier,
        columns = GridCells.Fixed(6),
    ) {
        items(categoryIcons){ icon ->
            SvgImage(
                url = icon,
                modifier = Modifier
                    .padding(8.dp)
                    .size(50.dp)
                    .clickable(
                        onClick = {
                            onIconSelect(icon)
                        }
                    ),
                tint = MaterialTheme.colorScheme.onBackground
            )
        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ColorsPicker(
    selectedHue: Float,
    onColorChange: (Float) -> Unit
){
    val color = IconColorsConstants.hslColor(hue = selectedHue)

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
    iconUrl: String?,
    iconColor: Color,
    onCategoryNameChange: (String) -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
    ) {
        CategoryIconColoredBackground(iconUrl = iconUrl, backgroundColor = iconColor, tint = Color.Black)
        Spacer(modifier = Modifier.width(10.dp))
        CustomOutlinedTextField(label = R.string.category_name, value = categoryName, onCategoryNameChange)
    }

}

@Composable
fun CustomOutlinedTextField(
    @StringRes label: Int,
    value: String,
    onChange: (String) -> Unit
){
    OutlinedTextField(
        value = value,
        label = { Text(stringResource(label)) },
        onValueChange = onChange,
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier.fillMaxWidth(),
        singleLine = true,
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color.Transparent,
            unfocusedContainerColor = Color.Transparent,
            disabledContainerColor = Color.Transparent,
            focusedTextColor = MaterialTheme.colorScheme.onBackground,
            focusedLeadingIconColor = MaterialTheme.colorScheme.onBackground,
            focusedPlaceholderColor = MaterialTheme.colorScheme.onBackground,
            unfocusedPlaceholderColor = MaterialTheme.colorScheme.onBackground,
            unfocusedTextColor = MaterialTheme.colorScheme.onBackground,
            unfocusedLeadingIconColor = MaterialTheme.colorScheme.onBackground,
            unfocusedLabelColor = MaterialTheme.colorScheme.onBackground.copy(alpha = .8f),
            unfocusedIndicatorColor = MaterialTheme.colorScheme.onBackground.copy(alpha = .8f)
        )
    )
}

@Preview
@Composable
fun IconsSearchingError(){
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Text(
            text = stringResource(R.string.error_while_retrieving_icons),
            style = MaterialTheme.typography.headlineSmall
        )

        Text(
            text = stringResource(R.string.check_network_connection),
            style = MaterialTheme.typography.bodyMedium
        )
    }
}


@Composable
fun NoIconsFound(query: String){
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Text(
            text = stringResource(R.string.no_icons_found),
            style = MaterialTheme.typography.headlineSmall
        )

        Text(
            text = if(query.length < 3) {
                stringResource(R.string.enter_at_least_3_characters)
            } else {
                stringResource(R.string.try_different_keyword)
            },
            style = MaterialTheme.typography.bodyMedium
        )
    }
}


@Composable
fun LoadingIcons(){
    CircularProgressIndicator()
}

