package cz.cvut.fit.cervem27.tasks.features.task.presentation.createTask

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.runtime.Composable
import androidx.compose.material3.Text
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.ComposeCompilerApi
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import cz.cvut.fit.cervem27.tasks.R
import cz.cvut.fit.cervem27.tasks.features.category.domain.Category
import cz.cvut.fit.cervem27.tasks.features.category.domain.CategoryIcon
import cz.cvut.fit.cervem27.tasks.features.category.presentation.categoriesCreate.CategoryImage
import org.koin.androidx.compose.koinViewModel
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.*

@Composable
fun CreateEditTask(
    viewModel: CreateTaskViewModel = koinViewModel(),
    navController: NavController
){
    val screenState by viewModel.stateStream.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier.padding(8.dp)
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

        
        
        Date(
            expanded = screenState.dateSelectExpanded,
            selectedDay = screenState.date,
            onSelect = viewModel::onSelectDate,
            dateValidator = viewModel::dateValidator,
            onExpandCalendarClick = viewModel::onShowCalendar
        )

        SelectedCategory(
            category = screenState.category,
            onSelectCategoryClick = viewModel::onSelectCategory
        )
        DropDown(
            expanded = screenState.categoriesSelectExpanded,
            onSelectCategory = viewModel::onSelectedCategory,
            screenState.categories
        )

       // Subtasks()

    }

}
//@Composable
//Subtasks(){
//
//}

@Preview
@Composable
fun ConfirmButtons(onCreateClick: () -> Unit = {}, onCancelClick: () -> Unit = {}){
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ){

        Icon(
            imageVector = Icons.Default.Close,
            contentDescription = null,
            modifier = Modifier
                .size(30.dp)
                .clip(CircleShape)
                .clickable(onClick = onCancelClick)

        )


        Button(
            onClick = {onCreateClick()},
        ) {
            Text(text = "Create")
        }
    }
}


@Composable
fun DropDown(
    expanded: Boolean,
    onSelectCategory: (Category) -> Unit,
    categories: List<Category>
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
        for(category in categories){
            DropdownMenuItem(
                text = { Text(text = category.categoryName) },
                leadingIcon = { CategoryImage(categoryIcon = category.categoryIcon) },
                onClick = { onSelectCategory(category) }
            )
        }

    }

}
// PastOrPresentSelectableDates.kt

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Date(
    expanded: Boolean,
    selectedDay: Date?,
    onSelect: (Date?)->Unit,
    onExpandCalendarClick: () -> Unit,
    dateValidator: (Long) -> Boolean

){
    val dateState = rememberDatePickerState()
    Row(
        modifier = Modifier
            .clickable(
                onClick = onExpandCalendarClick
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Deadline: ${formattedDate(selectedDay)}",
            fontSize = 18.sp
        )
        Spacer(modifier = Modifier.weight(1f))
        Icon(
            painter = painterResource(id = R.drawable.baseline_calendar_month_24),
            contentDescription = "date picker",
            modifier = Modifier.size(40.dp)
        )
    }
    
    if(expanded){ 
        DatePickerDialog(
            onDismissRequest = {
            },
            confirmButton = {
                TextButton(onClick = {
                    onSelect(dateState.selectedDateMillis?.let { Date(it) })
                }) {
                    Text(text = "ok")
                }
            }
        ){
            DatePicker(
                state = dateState,
                title = {},

                showModeToggle = true,
                dateValidator = dateValidator
            )
        }
    }

}

fun formattedDate(date: Date?): String {
    date?.let{
        val format = SimpleDateFormat("dd.MM.", Locale.getDefault())
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
            .fillMaxWidth()
    )
}

@Composable
fun SelectedCategory(
    category: Category?,
    onSelectCategoryClick: () -> Unit
){
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onSelectCategoryClick)
    ) {
        category?.let {
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                CategoryImage(
                    categoryIcon = category.categoryIcon
                )
                Spacer(modifier = Modifier.width(10.dp))
                Text(
                    text = category.categoryName,
                    fontSize = 20.sp,
                    color = Color.White

                )
            }
        }?:run {
            Text(text = "Select category...",
                fontSize = 20.sp,
                color = Color.White,
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}

