package cz.cvut.fit.cervem27.tasks.features.task.presentation

import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.runtime.Composable
import androidx.compose.material3.Text
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import cz.cvut.fit.cervem27.tasks.R
import cz.cvut.fit.cervem27.tasks.features.category.presentation.categoriesCreate.CategoryIcon
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
        },
        bottomBar = { Spacer(modifier = Modifier.height(0.dp))},
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
        CategoryIcon(
            icon = cz.cvut.fit.cervem27.tasks.features.category.domain.Icon(
                url = "https://api.iconify.design/mdi:injection-off.svg",
                color = Color.Red
            ),
            modifier = Modifier
            .padding(8.dp)
            .size(50.dp)
        )
        Spacer(modifier = Modifier.width(10.dp))
        Text(
            text = "Category",
            fontSize = 20.sp,
            color = Color.White

        )

    }
}
//0xFF343434