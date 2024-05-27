package cz.cvut.fit.cervem27.tasks.core

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import cz.cvut.fit.cervem27.tasks.features.task.presentation.TasksListScreen
import cz.cvut.fit.cervem27.tasks.features.task.presentation.CreateEditTask
import cz.cvut.fit.cervem27.tasks.features.category.presentation.categoriesList.CategoriesListScreen
import cz.cvut.fit.cervem27.tasks.features.category.presentation.categoriesCreate.CreateEditCategory
@Composable
fun Navigation(
    navController: NavHostController,
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
            TasksListScreen(navController = navController)
        }
        composable(route =  Screen.CategoriesListScreen.route){
            CategoriesListScreen(navController = navController)
        }
        composable(route =  Screen.TasksCreateScreen.route){
            CreateEditTask(navController = navController)
        }
        composable(route =  Screen.CategoriesCreateScreen.route){
            CreateEditCategory(navController = navController)
        }

    }
    
}