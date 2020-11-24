package ir.malv.cleanmovies.ui.screens.main

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.platform.setContent
import androidx.navigation.compose.*
import com.samskivert.mustache.Mustache
import dagger.hilt.android.AndroidEntryPoint
import ir.malv.cleanmovies.ui.utils.CleanMoviesTheme

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val argBundle = navBackStackEntry?.arguments
            val currentRoute = argBundle?.getString(KEY_ROUTE)
            CleanMoviesTheme {
                // A surface container using the 'background' color from the theme
                Scaffold(
                    topBar = {
                        TopAppBar(title = {
                            Text(
                                getActualTopBarName(
                                    NavGraph.topBarName(currentRoute ?: "").name,
                                    argBundle
                                )
                            )
                        })
                    },
                    bottomBar = {
                        BottomAppBar {
                            NavGraph.bottomBar().forEach { screen ->
                                BottomNavigationItem(
                                    icon = { Icon(screen.vectorAsset) },
                                    label = { Text(screen.name) },
                                    selected = currentRoute == screen.route,
                                    onClick = {
                                        navController.popBackStack(
                                            navController.graph.startDestination,
                                            false
                                        )
                                        if (currentRoute != screen.route) {
                                            navController.navigate(screen.route)
                                        }
                                    })
                            }
                        }
                    }
                ) {
                    NavHost(
                        navController = navController,
                        startDestination = NavGraph.Home()
                    ) {
                        composable(NavGraph.Home()) {
                            HomeScreen(
                                viewModel = viewModel,
                                onMovie = { movieId ->
                                    navController.navigate(NavGraph.MovieDetail.route + "/$movieId")
                                },
                                onAllMovies = {
                                    navController.navigate(NavGraph.Movies.route + "?genre=All")
                                },
                                onAllGenres = {
                                    navController.navigate(NavGraph.Genres.route)
                                },
                                onGenre = { genre, id ->
                                    navController.navigate(NavGraph.Movies.route + "?genre=$genre&genre_id=$id")
                                }
                            )
                        }
                        composable(NavGraph.Settings()) {
                            SettingsScreen(mainViewModel = viewModel)
                        }
                        composable(
                            NavGraph.MovieDetail(),
                            arguments = NavGraph.MovieDetail.args
                        ) {
                            MovieDetailScreen(
                                viewModel = viewModel,
                                movieId = it.arguments?.getInt("movie_id", -1) ?: -1
                            )
                        }
                        composable(NavGraph.Genres(), arguments = NavGraph.Genres.args) {
                            AllGenreScreen(viewModel = viewModel) { genre, id ->
                                navController.navigate(NavGraph.Movies.route + "?genre=$genre&genre_id=$id")
                            }
                        }
                        composable(NavGraph.Movies(), arguments = NavGraph.Movies.args) {
                            val args = it.arguments ?: Bundle()
                            AllMoviesScreen(
                                viewModel = viewModel,
                                genreName = args.getString("genre", "") ?: "",
                                genreId = args.getInt("genre_id", -1),
                                onMovieClicked = { movieId ->
                                    navController.navigate(NavGraph.MovieDetail.route + "/$movieId")
                                })
                        }
                    }
                }
            }
        }
    }

    /**
     * Find the argument between {{}} and replace it with the one in bundle
     * for instance Mr {{name}} will result in Mr ${bundle.getString("name")}
     * **NOTE** works for only one arg
     */
    private fun getActualTopBarName(template: String, bundle: Bundle?): String {
        if (bundle == null) return template
        val t = Mustache.compiler().compile(template)
        val m = mutableMapOf<String, Any>()
        for (i in bundle.keySet()) {
            bundle.get(i)?.let { m.put(i, it) }
        }
        return t.execute(m)
    }
}