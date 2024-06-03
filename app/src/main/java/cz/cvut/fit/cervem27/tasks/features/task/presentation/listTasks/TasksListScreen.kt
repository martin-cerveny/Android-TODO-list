package cz.cvut.fit.cervem27.tasks.features.task.presentation.listTasks

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import cz.cvut.fit.cervem27.tasks.R
import cz.cvut.fit.cervem27.tasks.core.Screen
import cz.cvut.fit.cervem27.tasks.core.ui.SwipeToDismissContainer
import cz.cvut.fit.cervem27.tasks.features.category.presentation.CategoryIconColoredBackground
import cz.cvut.fit.cervem27.tasks.features.category.presentation.IconColorsConstants
import cz.cvut.fit.cervem27.tasks.features.task.data.db.Converters
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

    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        },
        topBar = {
            TopAppBar(title = { Text(text = stringResource(R.string.tasks)) })
        },
        bottomBar = { Spacer(modifier = Modifier.height(0.dp))},
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.navigate(Screen.TasksCreateScreen.route) },
                containerColor = Color(0xFF0591FF),
                contentColor = Color.Black
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = null)
            }
        }
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
                                        message = "Task deleted.",
                                        actionLabel = "Undo",
                                        duration = SnackbarDuration.Short
                                    )
                                when (result) {
                                    SnackbarResult.ActionPerformed -> {
                                        viewModel.undoDelete(task)
                                    }

                                    SnackbarResult.Dismissed -> {
                                        /* Handle snackbar dismissed */
                                    }
                                }
                            }

                        },
                        painter = painterResource(id = R.drawable.icon_park_outline__check_one),
                        backgroundColor = Color(0xFF4CAF50),
                        padding = PaddingValues(vertical = 10.dp)
                    ) {
                        TaskCard(
                            task = task,
                            today = today,
                            modifier = Modifier
                                .combinedClickable(
                                    onClick = {},
                                    onLongClick = {
                                        navController.navigate(Screen.TasksEditScreen.route + "/${task.taskId}")
                                    }
                                )
                        )
                    }
                }

            }

        }
    }


}

@Composable
fun TaskCard(task: Task, today: Long, modifier: Modifier = Modifier){
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)

    ){
        Row(
            verticalAlignment = Alignment.CenterVertically
        ){
            CategoryIconColoredBackground(
               iconUrl =  task.category?.url,
                backgroundColor = task.category?.let{ IconColorsConstants.hslColor(it.colorHue) }
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
                Text(text = category.categoryName)
            }
            Spacer(modifier = Modifier.weight(1f))
            Log.d("today", "${task.date?.time?:0 - today}") // todo
            task.date?.let{date ->
                Text(text = stringResource(
                    R.string.d,
                    TimeUnit.MILLISECONDS.toDays(date.time - today)
                ))
            }
        }
        Text(
            text = task.name,
            color = Color.White,
            fontSize = 18.sp

        )
    }
}

