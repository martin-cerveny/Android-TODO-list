package cz.cvut.fit.cervem27.tasks.features.task.presentation.createTask

import androidx.compose.foundation.background
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.runtime.Composable
import androidx.compose.material3.Text
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import cz.cvut.fit.cervem27.tasks.R
import cz.cvut.fit.cervem27.tasks.core.ui.theme.IconColorsConstants
import cz.cvut.fit.cervem27.tasks.features.category.domain.Category
import cz.cvut.fit.cervem27.tasks.features.category.presentation.CategoryIconColoredBackground
import cz.cvut.fit.cervem27.tasks.features.category.presentation.categoriesCreate.ConfirmButtons
import cz.cvut.fit.cervem27.tasks.features.category.presentation.categoriesCreate.CustomOutlinedTextField
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
        modifier = Modifier.padding(8.dp)
    ) {

        ConfirmButtons(
            onConfirm = {
                viewModel.addTask()
                navController.navigateUp()
            },
            onCancel = {navController.navigateUp()}
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
            categories =  screenState.categories
        )


    }

}



@Composable
fun DropDown(
    expanded: Boolean,
    onSelectCategory: (Category?) -> Unit,
    categories: List<Category>
){
    DropdownMenu(
        expanded = expanded,
        onDismissRequest = { onSelectCategory(null) },
        offset = DpOffset(0.dp, 0.dp),
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.background)
    ) {
        DropdownItem(category = null, onSelectCategory = onSelectCategory)
        for (category in categories) {
            DropdownItem(category = category, onSelectCategory = onSelectCategory)
        }

    }
}

@Composable
fun DropdownItem(
    category: Category?,
    onSelectCategory: (Category?) -> Unit
){
    DropdownMenuItem(
        text = {
            Text(
                text = category?.categoryName?: stringResource(id = R.string.no_category),
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSecondaryContainer
            )
       },
        leadingIcon = { CategoryIconColoredBackground(
            iconUrl = category?.iconUrl,
            backgroundColor = category?.let{ IconColorsConstants.hslColor(it.colorHue) }
        ) },
        onClick = { onSelectCategory(category) },
    )
}



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
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = stringResource(R.string.deadline, formattedDate(selectedDay)),
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onBackground
        )
        Spacer(modifier = Modifier.weight(1f))
        IconButton(
          onClick = onExpandCalendarClick
        ) {
            Icon(
                painter = painterResource(id = R.drawable.baseline_calendar_month_24),
                contentDescription = stringResource(R.string.date_picker),
                modifier = Modifier.size(40.dp),
                tint = MaterialTheme.colorScheme.primary
            )
        }
    }
    
    if(expanded){ 
        DatePickerDialog(
            onDismissRequest = {
            },
            confirmButton = {
                TextButton(onClick = {
                    onSelect(dateState.selectedDateMillis?.let { Date(it) })
                }) {
                    Text(text = stringResource(R.string.ok))
                }
            },
            colors = DatePickerDefaults.colors(
                containerColor = MaterialTheme.colorScheme.primaryContainer
            )

        ){
            DatePicker(
                state = dateState,
                showModeToggle = false,
                dateValidator = dateValidator,
                colors = DatePickerDefaults.colors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                    headlineContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                    weekdayContentColor = MaterialTheme.colorScheme.primary,
                    subheadContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                    yearContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                    currentYearContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                    selectedYearContentColor = MaterialTheme.colorScheme.onPrimary,
                    selectedYearContainerColor = MaterialTheme.colorScheme.primary,
                    dayContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                    disabledDayContentColor = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.5f),
                    selectedDayContentColor = MaterialTheme.colorScheme.onPrimary,
                    disabledSelectedDayContentColor = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.5f),
                    selectedDayContainerColor = MaterialTheme.colorScheme.primary,
                    disabledSelectedDayContainerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.5f),
                    todayContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                    todayDateBorderColor = MaterialTheme.colorScheme.primary,
                    dayInSelectionRangeContentColor = MaterialTheme.colorScheme.primary,
                    dayInSelectionRangeContainerColor = MaterialTheme.colorScheme.primary
                )

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

    CustomOutlinedTextField(
        label = R.string.task,
        value = taskName,
        onChange = onTaskNameChange
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SelectedCategory(
    category: Category?,
    onSelectCategoryClick: () -> Unit
){
    Card(
        modifier = Modifier
            .fillMaxWidth(),
        onClick = onSelectCategoryClick,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.secondaryContainer
        )
    ) {

        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            CategoryIconColoredBackground(
                iconUrl = category?.iconUrl,
                backgroundColor = category?.let {category ->
                    IconColorsConstants.hslColor(category.colorHue)
                }
            )
            Spacer(modifier = Modifier.width(10.dp))
            Text(
                text = category?.categoryName?: stringResource(R.string.no_category),
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSecondaryContainer

            )
        }
    }
}

