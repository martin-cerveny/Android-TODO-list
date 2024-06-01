package cz.cvut.fit.cervem27.tasks.core

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import cz.cvut.fit.cervem27.tasks.features.task.presentation.listTasks.TasksListScreen
import cz.cvut.fit.cervem27.tasks.features.task.presentation.createTask.CreateEditTask
import cz.cvut.fit.cervem27.tasks.features.category.presentation.categoriesList.CategoriesListScreen
import cz.cvut.fit.cervem27.tasks.features.category.presentation.categoriesCreate.CreateEditCategory
@Composable
fun Navigation(
    navController: NavHostController,
    snackbarHostState: SnackbarHostState,
    modifier: Modifier = Modifier
){
    NavHost(
        navController = navController,
        modifier = modifier,
        startDestination = Screen.TasksListScreen.route,
//        enterTransition = {
//            EnterTransition.None
//        },
//        exitTransition = {
//            ExitTransition.None
//        }
    ){
        composable(route =  Screen.TasksListScreen.route){
            TasksListScreen(navController = navController, snackbarHostState = snackbarHostState)
        }
        composable(route =  Screen.CategoriesListScreen.route){
            CategoriesListScreen(navController = navController)
        }

        composable(
            route = Screen.TasksEditScreen.route + "/{${Screen.TasksEditScreen.ID_KEY}}",
            arguments = listOf(
                navArgument(name = Screen.TasksEditScreen.ID_KEY) {
                    type = NavType.LongType
                },
            ),
        ) {
            CreateEditTask(navController = navController)
        }

        composable(route =  Screen.TasksCreateScreen.route){
            CreateEditTask(navController = navController)
        }

        composable(
            route = Screen.CategoriesEditScreen.route + "/{${Screen.CategoriesEditScreen.ID_KEY}}",
            arguments = listOf(
                navArgument(name = Screen.CategoriesEditScreen.ID_KEY) {
                    type = NavType.LongType
                },
            ),
        ) {
            CreateEditCategory(navController = navController)
        }

        composable(route =  Screen.CategoriesCreateScreen.route){
            CreateEditCategory(navController = navController)
        }

    }
    
}