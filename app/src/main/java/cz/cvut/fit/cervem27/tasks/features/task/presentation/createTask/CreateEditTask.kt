package cz.cvut.fit.cervem27.tasks.features.task.presentation.createTask

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.runtime.Composable
import androidx.compose.material3.Text
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
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Popup
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import cz.cvut.fit.cervem27.tasks.R
import cz.cvut.fit.cervem27.tasks.features.category.domain.Category
import cz.cvut.fit.cervem27.tasks.features.category.presentation.categoriesCreate.CategoryIcon
import cz.cvut.fit.cervem27.tasks.features.category.presentation.categoriesList.CategoryCard
import kotlinx.coroutines.delay
import org.koin.androidx.compose.koinViewModel
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun CreateEditTask(
    viewModel: CreateTaskViewModel = koinViewModel(),
    navController: NavController
){
    val screenState by viewModel.stateStream.collectAsStateWithLifecycle()

    Column(

    ) {

        ConfirmButtons(
            onCreateClick = {
                viewModel.addTask()
                navController.navigateUp()

            },
            onCancelClick = {navController.navigateUp()}
        )
        TaskEntry(
            screenState.taskName,
            viewModel::onTaskNameChange
        )
        Spacer(modifier = Modifier.height(20.dp))



        Date(onDismiss = viewModel::hideCalendar)
//        Spacer(modifier = Modifier.weight(1f))


        SelectedCategory(onSelectCategoryClick = viewModel::onSelectCategory)
        DropDown(
            expanded = screenState.categoriesSelectExpanded,
            onSelectCategory = viewModel::onSelectedCategory
        )

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
            onClick = {onCreateClick()},
            modifier = Modifier
                .padding(8.dp)

        ) {
            Text(text = "Create")
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropDown(
    expanded: Boolean,
    onSelectCategory: (String) -> Unit
){


    DropdownMenu(
        expanded = expanded,
        onDismissRequest = { /*TODO*/ },
        offset = DpOffset(0.dp, 0.dp),
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize()
            .background(Color(0xFF212121), shape = RoundedCornerShape(16.dp))
    ) {
        for(item in listOf("item1", )){
            DropdownMenuItem(text = { Text(text = item) }, onClick = { onSelectCategory(item) })
        }

    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Date(onDismiss: ()->Unit){
    val dateState = rememberDatePickerState()
//    Popup(
//        onDismissRequest = onDismiss
//    ) {
//        DatePicker(
//            state = dateState,
//            title = {},
//            showModeToggle = true,
//        )
//    }
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
fun TaskEntry(
    taskName: String,
    onTaskNameChange: (String) -> Unit
){
    OutlinedTextField(
        value = taskName,
        label = { Text(text= stringResource(R.string.task)) },
        onValueChange = {onTaskNameChange(it)},
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
    )
}

@Composable
fun SelectedCategory(
    onSelectCategoryClick: () -> Unit
){



    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.clickable(
            onClick = onSelectCategoryClick
        )


    ) {
        CategoryIcon(
            icon = cz.cvut.fit.cervem27.tasks.features.category.domain.Icon(
                url = "https://api.iconify.design/mdi:injection-off.svg",
                color = Color(0xFFFF9800)
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