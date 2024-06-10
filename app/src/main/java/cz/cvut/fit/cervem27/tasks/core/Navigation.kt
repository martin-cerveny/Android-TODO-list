package cz.cvut.fit.cervem27.tasks.core

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import cz.cvut.fit.cervem27.tasks.features.task.presentation.listTasks.TasksListScreen
import cz.cvut.fit.cervem27.tasks.features.task.presentation.createTask.CreateEditTaskScreen
import cz.cvut.fit.cervem27.tasks.features.category.presentation.categoriesList.CategoriesListScreen
import cz.cvut.fit.cervem27.tasks.features.category.presentation.createCategory.CreateEditCategoryScreen
@Composable
fun Navigation(
    navController: NavHostController,
     modifier: Modifier = Modifier
){
    NavHost(
        navController = navController,
        startDestination = Screen.TasksListScreen.route,
        modifier = modifier,
    ){
        composable(route =  Screen.TasksListScreen.route){
            TasksListScreen(navController = navController)
        }
        //------------------------------------------------------------------------------------------
        composable(route =  Screen.CategoriesListScreen.route){
            CategoriesListScreen(navController = navController)
        }
        //------------------------------------------------------------------------------------------
        composable(
            route = Screen.TasksEditScreen.route + "/{${Screen.TasksEditScreen.ID_KEY}}",
            arguments = listOf(
                navArgument(name = Screen.TasksEditScreen.ID_KEY) {
                    type = NavType.LongType
                },
            ),
        ) {
            CreateEditTaskScreen(navController = navController)
        }
        //------------------------------------------------------------------------------------------
        composable(route =  Screen.TasksCreateScreen.route){
            CreateEditTaskScreen(navController = navController)
        }
        //------------------------------------------------------------------------------------------
        composable(
            route = Screen.CategoriesEditScreen.route + "/{${Screen.CategoriesEditScreen.ID_KEY}}",
            arguments = listOf(
                navArgument(name = Screen.CategoriesEditScreen.ID_KEY) {
                    type = NavType.LongType
                },
            ),
        ) {
            CreateEditCategoryScreen(navController = navController)
        }
        //------------------------------------------------------------------------------------------
        composable(route =  Screen.CategoriesCreateScreen.route){
            CreateEditCategoryScreen(navController = navController)
        }
        //------------------------------------------------------------------------------------------
    }
    
}