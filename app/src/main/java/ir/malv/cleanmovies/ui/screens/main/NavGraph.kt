package ir.malv.cleanmovies.ui.screens.main

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Category
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Movie
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.VectorAsset
import androidx.navigation.NavType
import androidx.navigation.compose.NamedNavArgument
import androidx.navigation.compose.navArgument

sealed class NavGraph(
    val route: String,
    val name: String,
    val vectorAsset: VectorAsset,
    val id: String = route,
    val color: Color = Color.Transparent,
    val args: List<NamedNavArgument> = emptyList()
) {
    object Home : NavGraph(
        route = "/home",
        name = "Home",
        vectorAsset = Icons.Default.Home,
    )

    object Genres: NavGraph(
        route =  "/genres",
        name = "Genres",
        vectorAsset = Icons.Default.Category
    )
    object Settings: NavGraph(
        route = "/settings",
        name = "Settings",
        vectorAsset = Icons.Default.Settings
    )

    object MovieDetail: NavGraph(
        route = "/movie",
        id = "/movie/{movie_id}",
        name = "Movie Info",
        vectorAsset = Icons.Default.Movie,
        args = listOf(
            navArgument(
                name = "movie_id"
            ) {
                type = NavType.IntType
                nullable = false
            }
        )
    )

    object Movies: NavGraph(
        route = "/movies",
        id = "/movies?genre={genre}&genre_id={genre_id}",
        name = "{{genre}} Movies",
        args = listOf(
            navArgument("genre") {
                type = NavType.StringType
                nullable = true
                defaultValue = "All"
            },
            navArgument("genre_id") {
                type = NavType.IntType
                nullable = false
                defaultValue = -1
            },
        ),
        vectorAsset = Icons.Default.Movie
    )

    operator fun invoke(): String = id


    companion object {
        fun bottomBar(): List<NavGraph> = listOf(
            Home,
            Genres,
            Settings
        )

        private fun allScreens() = listOf(
            Home,
            Genres,
            Settings,
            MovieDetail,
            Movies
        )

        fun topBarName(route: String): NavGraph {
            return allScreens().find { it.id == route } ?: Home
        }
    }
}