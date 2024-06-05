package cz.cvut.fit.cervem27.tasks

import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import cz.cvut.fit.cervem27.tasks.core.Navigation
import cz.cvut.fit.cervem27.tasks.core.Screen
import cz.cvut.fit.cervem27.tasks.features.notification.presentation.Permission

@Composable
fun MainScreen() {


    val navController = rememberNavController()
    val currentEntry by navController.currentBackStackEntryAsState()
    val currentEntryRoute = currentEntry?.destination?.route
    val shouldShowBottomNavigation = currentEntryRoute?.let(::hasBottomNavigation) ?: false


    Permission()

    Scaffold(

        bottomBar = {
            if (shouldShowBottomNavigation) {
                BottomAppBar(containerColor = MaterialTheme.colorScheme.primaryContainer) {
                    NavigationBarItem(
                        painter = painterResource(id = R.drawable.baseline_task_alt_24),
                        name = stringResource(id = R.string.tasks),
                        selected = currentEntryRoute == Screen.TasksListScreen.route,
                        onClick = {
                            navController.navigate(Screen.TasksListScreen.route) {
                                popUpTo(navController.graph.startDestinationId) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    )

                    NavigationBarItem(
                        painter = painterResource(id = R.drawable.baseline_category_24),
                        name = stringResource(id = R.string.categories),
                        selected = currentEntryRoute == Screen.CategoriesListScreen.route,
                        onClick = {
                            navController.navigate(Screen.CategoriesListScreen.route) {
                                popUpTo(navController.graph.startDestinationId) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                    )

                }
            }
        }
    ) { innerPadding ->

        Navigation(
            navController = navController,
            modifier = Modifier.padding(innerPadding)
        )
    }
}

private fun hasBottomNavigation(route: String): Boolean {
    return route in listOf(
        Screen.TasksListScreen.route,
        Screen.CategoriesListScreen.route
    )
}

@Composable
private fun RowScope.NavigationBarItem(
    painter: Painter,
    name: String,
    selected: Boolean,
    onClick: () -> Unit,
) {
    val contentColor = if (selected) {
        MaterialTheme.colorScheme.primary
    } else {
        MaterialTheme.colorScheme.tertiary
    }

    NavigationBarItem(
        selected = selected,
        onClick = onClick,
        icon = {
            Icon(painter = painter, contentDescription = name, tint = contentColor)
        },
        label = {
            Text(text = name, style = MaterialTheme.typography.labelMedium, color = contentColor)
        }
    )


}