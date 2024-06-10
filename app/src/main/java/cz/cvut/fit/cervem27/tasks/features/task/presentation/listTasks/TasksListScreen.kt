package cz.cvut.fit.cervem27.tasks.features.task.presentation.listTasks

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import cz.cvut.fit.cervem27.tasks.R
import cz.cvut.fit.cervem27.tasks.core.Screen
import cz.cvut.fit.cervem27.tasks.core.ui.theme.IconColorsConstants
import cz.cvut.fit.cervem27.tasks.features.category.presentation.CategoryIconColoredBackground
import cz.cvut.fit.cervem27.tasks.features.task.domain.Task
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel
import java.util.concurrent.TimeUnit


@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun TasksListScreen(
    viewModel: TasksListViewModel = koinViewModel(),
    navController: NavController
){
    val screenState by viewModel.stateStream.collectAsStateWithLifecycle()
    val today: Long = viewModel.today
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }
    val context = LocalContext.current

    Scaffold(

        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        },
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.tasks),
                        style = MaterialTheme.typography.headlineSmall,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                )
            )
        },
        bottomBar = { Spacer(modifier = Modifier.height(0.dp))},
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.navigate(Screen.TasksCreateScreen.route) },
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = stringResource(R.string.add_task))
            }
        },

        contentColor = MaterialTheme.colorScheme.secondaryContainer,

        ) { padding ->
        LazyColumn(
            modifier = Modifier
                .padding(padding)
       ) {
            items(
                items = screenState.tasks,
                key = { it.taskId }
            ) { task ->

                Box(
                    modifier = Modifier.animateItemPlacement()
                ) {
                    SwipeToDismissContainer(
                        item = task,
                        onDelete = { task ->
                            viewModel.delete(task)
                            scope.launch {
                                val result = snackbarHostState
                                    .showSnackbar(
                                        message = context.getString(R.string.task_deleted),
                                        actionLabel = context.getString(R.string.undo),
                                        duration = SnackbarDuration.Short
                                    )
                                when (result) {
                                    SnackbarResult.ActionPerformed -> {
                                        viewModel.undoDelete(task)
                                    }

                                    SnackbarResult.Dismissed -> {}
                                }
                            }

                        },
                        painter = painterResource(id = R.drawable.icon_park_outline__check_one),
                        painterTint = MaterialTheme.colorScheme.onTertiaryContainer,
                        backgroundColor = MaterialTheme.colorScheme.tertiaryContainer,
                        padding = PaddingValues(8.dp)
                    ) {
                        TaskCard(
                            task = task,
                            today = today,
                            onLongClick = {navController.navigate(Screen.TasksEditScreen.route + "/${task.taskId}")}
                        )
                    }
                }

            }

        }
    }


}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TaskCard(task: Task, today: Long, onLongClick: () -> Unit){
    Card(
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.secondaryContainer
        )
    ){
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .combinedClickable(
                    onClick = {},
                    onLongClick = onLongClick
                )
        ){
            CategoryIconColoredBackground(
               iconUrl =  task.category?.iconUrl,
               backgroundColor = task.category?.let{ IconColorsConstants.hslColor(it.colorHue) },
                modifier = Modifier.padding(8.dp)
            )
            TaskDetails(
                task = task,
                today = today
            )
        }
    }
}

@Composable
fun TaskDetails(task: Task, today: Long){
    Column(
        Modifier.padding(8.dp)
    ) {
        Row{
            task.category?.let {category ->
                Text(
                    text = category.categoryName,
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onSecondaryContainer,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.weight(1f)
                )
            }?:run{
                Spacer(modifier = Modifier.weight(1f))
            }

            task.date?.let{date ->
                val remainingMillis = date.time - today
                // negative remaining days are rounded up
                val remainingDays = TimeUnit.MILLISECONDS.toDays(remainingMillis) - if(remainingMillis < 0) 1 else 0
                    Text(
                        text = stringResource(R.string.d, remainingDays),
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.primary,
                    )
                }
        }
        Text(
            text = task.name,
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSecondaryContainer,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,

        )
    }
}

