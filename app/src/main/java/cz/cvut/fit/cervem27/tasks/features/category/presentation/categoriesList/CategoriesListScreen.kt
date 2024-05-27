package cz.cvut.fit.cervem27.tasks.features.category.presentation.categoriesList

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import cz.cvut.fit.cervem27.tasks.R
import cz.cvut.fit.cervem27.tasks.core.Screen
import cz.cvut.fit.cervem27.tasks.features.category.domain.Category
import cz.cvut.fit.cervem27.tasks.features.category.domain.categories
import cz.cvut.fit.cervem27.tasks.features.category.presentation.categoriesCreate.CategoryIcon
import org.koin.androidx.compose.koinViewModel

@Composable
fun MySvgImage(url: String, modifier: Modifier = Modifier, tint: Color) {
    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(url)
            .decoderFactory(SvgDecoder.Factory())
            .build(),
        contentDescription = null,
        modifier = modifier,
        colorFilter = ColorFilter.tint(tint)
    )
}


@OptIn(ExperimentalMaterial3Api::class)
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
            LazyColumn(
                modifier = Modifier.padding(padding)
            ) {
                items(screenState.categories){ category ->
                    CategoryCard(category)
                }
            }
        }


}

@Composable
fun CategoryCard(category: Category){
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 4.dp)

    ){
        Row(
            verticalAlignment = Alignment.CenterVertically
        ){
            CategoryIcon(icon = category.icon, modifier = Modifier
                .size(60.dp)
                .padding(8.dp))

            Spacer(modifier = Modifier.width(8.dp))
            Text(text = category.categoryName)
            
          
        }
    }
}

@Composable
fun ActionButton(icon: ImageVector, color: Color, onClick: () -> Unit){
    Button(
        onClick = onClick,
        shape = CircleShape,
        contentPadding = PaddingValues(0.dp),
        modifier = Modifier
            .size(40.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor =  color.copy(alpha = 0.3f),
            contentColor =  color,
        )
    ) {
        Icon(imageVector = icon, contentDescription = null)
    }
}