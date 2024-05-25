package cz.cvut.fit.cervem27.tasks.features.category.presentation

import android.graphics.drawable.Icon
import android.text.InputFilter
import android.widget.GridLayout
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
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
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ModifierInfo
import androidx.compose.ui.layout.VerticalAlignmentLine
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.android.gms.tagmanager.Container
import cz.cvut.fit.cervem27.tasks.R

@Preview
@Composable
fun CreateEditCategory(){
    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize()
    ){
        Spacer(modifier = Modifier.height(40.dp))
        Header()
        Spacer(modifier = Modifier.height(20.dp))
        ColorsPicker()
        Spacer(modifier = Modifier.height(20.dp))
        SearchIconHeader()
        Spacer(modifier = Modifier.height(20.dp))
        SearchIconResults(modifier = Modifier.weight(1f))
        Spacer(modifier = Modifier.height(20.dp))
        addCancelIcons()
    }
}

@Composable
fun addCancelIcons(){
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ){
        Button(onClick = { /*TODO*/ }) {
            Text(text = "Cancel")
        }
        Button(onClick = { /*TODO*/ }) {
            Text(text = "Create")
        }
    }
}
@Composable
fun SearchIconHeader(){
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.height(70.dp)
    ) {

        OutlinedTextField(
            value = "shopping",
            label = { Text(stringResource(R.string.search_icon)) },
            onValueChange = {},
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier
                .fillMaxWidth(),
            trailingIcon = {
                Icon( imageVector = Icons.Default.Clear , contentDescription = null)
            }
        )
    }

}

@Composable
fun SearchIconResults(modifier: Modifier = Modifier){
    val icons: List<ImageVector> = listOf(Icons.Default.AccountCircle,Icons.Default.AccountCircle,Icons.Default.AccountCircle,Icons.Default.AccountCircle,Icons.Default.AccountCircle,Icons.Default.AccountCircle,Icons.Default.AccountCircle,Icons.Default.AccountCircle,Icons.Default.AccountCircle,Icons.Default.AccountCircle,Icons.Default.AccountCircle,Icons.Default.AccountCircle,Icons.Default.AccountCircle,Icons.Default.AccountCircle,Icons.Default.AccountCircle,Icons.Default.AccountCircle,Icons.Default.AccountCircle,Icons.Default.AccountCircle,Icons.Default.AccountCircle,Icons.Default.AccountCircle,Icons.Default.AccountCircle,Icons.Default.AccountCircle,Icons.Default.AccountCircle,Icons.Default.AccountCircle,Icons.Default.AccountCircle,Icons.Default.AccountCircle,Icons.Default.AccountCircle,Icons.Default.AccountCircle,Icons.Default.AccountCircle,Icons.Default.AccountCircle,)

    LazyVerticalGrid(
        modifier = modifier
            .background(color = Color(0xFF343434), shape = RoundedCornerShape(16.dp)),
        columns = GridCells.Fixed(6),
    ) {
        items(icons){icon ->
            Icon(
                imageVector = icon,
                contentDescription = null,
                modifier = Modifier.size(50.dp)
            )

        }
    }

}

@Composable
fun ColorsPicker(){
    val circleSize = 40.dp

    val colors = listOf(
        Color(0xFFFD9854),
        Color(0xFFD15E5E),
        Color(0xFFD15EBB),
        Color(0xFF86DC86),
        Color(0xFF4DA8CB),
        Color(0xFFE1D5F2),

    )

    Row (
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ){
        for(color in colors){
           Box(modifier = Modifier
               .size(circleSize)
               .background(color = color.copy(alpha = 0.5f), shape = CircleShape)
           ){
               Box(
                   modifier = Modifier
                       .padding(4.dp)
                       .clip(CircleShape)
                       .fillMaxSize()
                       .clickable(onClick = {/*todo*/ })
                       .background(color = color)

               )
           }
        }
    }
}

@Composable
fun Header(){
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.height(60.dp)
    ) {
        CategoryIcon(
            modifier = Modifier
                .size(60.dp)
        )
        Spacer(modifier = Modifier.width(10.dp))
        OutlinedTextField(
            value = "Category",
            label = { Text(stringResource(R.string.category_name)) },
            onValueChange = {},
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier
                .fillMaxWidth()
        )
    }

}


@Composable
fun CategoryIcon(modifier: Modifier = Modifier){
    Box(
        modifier = modifier
            .background(color = Color(0xFFFD9854), shape = RoundedCornerShape(24.dp))

    ) {
        Icon(
            imageVector = Icons.Default.ShoppingCart,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.background,
            modifier = Modifier
                .padding(8.dp)
                .fillMaxSize(),
        )
    }
}