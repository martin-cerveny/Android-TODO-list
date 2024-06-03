package cz.cvut.fit.cervem27.tasks.core

sealed class Screen(val route: String) {
    data object TasksListScreen : Screen("tasksList")
    data object CategoriesListScreen : Screen("categoriesList")
    data object TasksCreateScreen : Screen("tasksCreate")
    data object CategoriesCreateScreen : Screen("categoriesCreate")
    data object TasksEditScreen : Screen("tasksEdit") {
        const val ID_KEY = "id"
    }
    data object CategoriesEditScreen : Screen("categoriesEdit") {
        const val ID_KEY = "id"
    }
}
