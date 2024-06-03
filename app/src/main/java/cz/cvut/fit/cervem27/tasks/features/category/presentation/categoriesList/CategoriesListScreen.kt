package cz.cvut.fit.cervem27.tasks.features.category.presentation.categoriesList

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import cz.cvut.fit.cervem27.tasks.R
import cz.cvut.fit.cervem27.tasks.core.Screen
import cz.cvut.fit.cervem27.tasks.features.category.domain.Category
import cz.cvut.fit.cervem27.tasks.features.category.presentation.CategoryIconColoredBackground
import org.koin.androidx.compose.koinViewModel
import cz.cvut.fit.cervem27.tasks.features.category.presentation.IconColorsConstants



@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun CategoriesListScreen(
    viewModel: CategoriesListViewModel = koinViewModel(),
    navController: NavController
){
    val screenState by viewModel.screenStateStream.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            TopAppBar(title = { Text(text = stringResource(R.string.categories))})
        },
        bottomBar = { Spacer(modifier = Modifier.height(0.dp))},
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.navigate(Screen.CategoriesCreateScreen.route) },
                containerColor = Color(0xFF0591FF),
                contentColor = Color.Black
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = null)
            }
        }
    ) { padding ->
        Box(
            modifier = Modifier.padding(padding)
        ) {
            LazyColumn {
                items(
                    items = screenState.categories,
                    key = { it.categoryId }
                ) { category ->

                    Box(
                        modifier = Modifier.animateItemPlacement()
                    ) {

                        CategoryCard(
                            category = category,
                            onEdit = {
                                navController.navigate(Screen.CategoriesEditScreen.route + "/${category.categoryId}")
                            },
                            onDelete = {
                                viewModel.onTryToDeleteCategory(category)
                            }
                        )

                    }
                }

            }
            if(screenState.categoryToBeDeleted != null) {
                DeleteCategoryConfirmationDialog(
                    onConfirm = viewModel::onDeleteConfirmation,
                    onCancel = viewModel::onDeleteCancelation
                )
            }
        }
    }

}

@Composable
fun CategoryCard(
    category: Category,
    onEdit: () -> Unit,
    onDelete: () -> Unit
){

    Row(
        verticalAlignment = Alignment.CenterVertically
    ){
        CategoryIconColoredBackground(
            iconUrl = category.url,
            backgroundColor = IconColorsConstants.hslColor(category.colorHue)
        )

        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = category.categoryName,
            modifier = Modifier.weight(1f),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,

        )
        IconButton(onClick = onEdit) {
            Icon(imageVector = Icons.Default.Edit, contentDescription = null)
        }
        IconButton(onClick = onDelete) {
            Icon(imageVector = Icons.Default.Delete, contentDescription = null)
        }
    }

}



@Composable
fun DeleteCategoryConfirmationDialog(
    onConfirm: () -> Unit,
    onCancel: () -> Unit
){
    AlertDialog(
        icon = {
            Icon(Icons.Default.Warning, contentDescription = "Warning")
        },
        title = {
            Text(text = "Delete category?")
        },
        text = {
            Text(text = "Category and all tasks belonging to it will be permanently deleted.")
        },
        onDismissRequest = onCancel,
        confirmButton = {
            TextButton(
                onClick = onConfirm
            ) {
                Text("Confirm")
            }
        },
        dismissButton = {
            TextButton(
                onClick = onCancel
            ) {
                Text("Cancel")
            }
        }
    )
}