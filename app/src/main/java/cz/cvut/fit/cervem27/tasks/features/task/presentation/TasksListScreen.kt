package cz.cvut.fit.cervem27.tasks.features.task.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import cz.cvut.fit.cervem27.tasks.R
import cz.cvut.fit.cervem27.tasks.core.Screen
import cz.cvut.fit.cervem27.tasks.features.category.Category
import cz.cvut.fit.cervem27.tasks.features.category.presentation.MySvgImage
import cz.cvut.fit.cervem27.tasks.features.task.domain.Task
import cz.cvut.fit.cervem27.tasks.features.task.domain.tasks


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TasksListScreen(
    navController: NavController
){
    Scaffold(
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
            modifier = Modifier.padding(padding)
        ) {
            items(tasks){ task ->
                TaskCard(task)
            }
        }
    }


}

@Composable
fun TaskCard(task: Task){
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 4.dp)

    ){
        Row(
            verticalAlignment = Alignment.CenterVertically
        ){
            MySvgImage(
                url = task.category.image,
                color = task.category.color,
                modifier = Modifier
                    .size(60.dp)
                    .padding(8.dp)
            )
            TaskDetails(task)


        }
        val progress = task.subtasks.count { it.completed }.toFloat() / task.subtasks.size
        LinearProgressIndicator(progress = progress)
    }
}

@Composable
fun TaskDetails(task: Task){
    Column(
        Modifier.padding(8.dp)
    ) {
        Row{
            Text(text = task.category.name)
            Spacer(modifier = Modifier.weight(1f))
            Text(text = "5d")
        }
        Text(
            text = task.name,
            color = Color.White,
            fontSize = 18.sp

        )
    }
}
