package cz.cvut.fit.cervem27.tasks.features.task.presentation

import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.runtime.Composable
import androidx.compose.material3.Text
import android.graphics.drawable.Icon
import android.text.InputFilter
import android.widget.GridLayout
import android.widget.Space
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
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
import androidx.compose.foundation.layout.wrapContentHeight
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
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDatePickerState
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
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.PopupProperties
import androidx.navigation.NavController
import com.google.android.gms.tagmanager.Container
import cz.cvut.fit.cervem27.tasks.R
import cz.cvut.fit.cervem27.tasks.features.category.presentation.CategoryIcon
import java.text.SimpleDateFormat
import java.util.*
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateEditTask(
    navController: NavController
){
    Scaffold(
        modifier = Modifier,
        topBar = {
            TopAppBar(
                title = {
                    SelectedCategory()
                    DropDown()
                },
                actions = {
                    Icon(
                        imageVector = Icons.Default.KeyboardArrowDown,
                        contentDescription = null,
                        modifier = Modifier.size(30.dp),
                        tint = Color.White
                    )
                }
                
            )
        }
    ) {
        Column(
            modifier = Modifier.padding(it)
        ) {
            Spacer(modifier = Modifier.height(20.dp))
            TaskEntry()
            Date()
            Spacer(modifier = Modifier.weight(1f))
            ConfirmButtons(
                onCreateClick = {navController.navigateUp()},
                onCancelClick = {navController.navigateUp()}
            )
        }
    }
}

@Composable
fun ConfirmButtons(onCreateClick: () -> Unit, onCancelClick: () -> Unit){
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ){
        Button(
            onClick = {onCancelClick()},
            modifier = Modifier
                .padding(8.dp)

        ) {
            Icon(
                imageVector = Icons.Default.Close,
                contentDescription = null,
            )
        }

        Button(
            onClick = {onCancelClick()},
            modifier = Modifier
                .padding(8.dp)

        ) {
            Text(text = "Create")
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropDown(){
    DropdownMenu(
        expanded = false,
        onDismissRequest = { /*TODO*/ },
        offset = DpOffset(0.dp, 0.dp),
        modifier = Modifier
            .fillMaxWidth()
    ) {
        DropdownMenuItem(text = { Text(text = "item1") }, onClick = { /*TODO*/ })
        DropdownMenuItem(text = { Text(text = "item1") }, onClick = { /*TODO*/ })
        DropdownMenuItem(text = { Text(text = "item1") }, onClick = { /*TODO*/ })
        DropdownMenuItem(text = { Text(text = "item1") }, onClick = { /*TODO*/ })
        DropdownMenuItem(text = { Text(text = "item1") }, onClick = { /*TODO*/ })
        DropdownMenuItem(text = { Text(text = "item1") }, onClick = { /*TODO*/ })


    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Date(){
    val dateState = rememberDatePickerState()
    DatePicker(
        state = dateState,
        title = {},
        headline = {
            Text(
                text = "Deadline ${formattedDate(dateState.selectedDateMillis)}",
                fontSize = 20.sp,
                modifier = Modifier.padding(16.dp)
            )
       },
        showModeToggle = false
    )
}

fun formattedDate(dateMillis: Long?): String {
    dateMillis?.let{
        val date = Date(dateMillis)
        val format = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        return format.format(date)
    }
    return ""
}

@Composable
fun TaskEntry(){
    OutlinedTextField(
        value = "Task",
        label = { Text(text= stringResource(R.string.task)) },
        onValueChange = {},
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
    )
}

@Composable
fun SelectedCategory(){

    Row(
        verticalAlignment = Alignment.CenterVertically,

    ) {
        CategoryIcon(modifier = Modifier
            .padding(8.dp)
            .size(50.dp))
        Spacer(modifier = Modifier.width(10.dp))
        Text(
            text = "Category",
            fontSize = 20.sp,
            color = Color.White

        )

    }
}
//0xFF343434