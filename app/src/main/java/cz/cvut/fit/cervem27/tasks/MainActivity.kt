package cz.cvut.fit.cervem27.tasks

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import cz.cvut.fit.cervem27.tasks.features.category.presentation.CategoriesListScreen
import cz.cvut.fit.cervem27.tasks.features.category.presentation.CreateEditCategory
import cz.cvut.fit.cervem27.tasks.features.task.presentation.CreateEditTask
import cz.cvut.fit.cervem27.tasks.features.task.presentation.TasksListScreen
import cz.cvut.fit.cervem27.tasks.ui.theme.TasksTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TasksTheme {

                   // CreateEditCategory()
//                    CreateEditTask()
               // CategoriesListScreen()
//                TasksListScreen
                MainScreen()
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    TasksTheme {

    }
}