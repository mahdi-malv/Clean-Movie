package ir.malv.cleanmovies.ui.screens.preload

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.ui.focus.ExperimentalFocus
import androidx.compose.ui.platform.setContent
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.*
import dagger.hilt.android.AndroidEntryPoint
import ir.malv.cleanmovies.ui.screens.main.MainActivity
import ir.malv.cleanmovies.ui.utils.CleanMovieSurface

@AndroidEntryPoint
class PreloadActivity : AppCompatActivity() {

    private val preloadViewModel: PreloadViewModel by viewModels()
    private lateinit var navController: NavController

    @ExperimentalFocus
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            navController = rememberNavController()
            CleanMovieSurface {
                NavHost(
                    navController = navController as NavHostController,
                    startDestination = "/",
                ) {
                    composable("/") {
                        SplashScreen(viewModel = preloadViewModel, onFinished = { alreadyLoggedIn ->
                            if (alreadyLoggedIn) moveToNextPage()
                            else {
                                navController.popBackStack()
                                navController.navigate("/login")
                            }
                        })
                    }

                    composable("/login") {
                        LoginScreen(viewModel = preloadViewModel, onLoggedIn = ::moveToNextPage, onMoveToRegister = {
                            navController.navigate("/register")
                        })
                    }
                    
                    composable("/register") {
                        RegistrationScreen(viewModel = preloadViewModel, onRegistered = ::moveToNextPage, onMoveBackToLogin = {
                            navController.popBackStack()
                        })
                    }
                }
            }
        }
    }

    private fun moveToNextPage() = startActivity(Intent(this, MainActivity::class.java)).also { finish() }

    private var onFirstPress = 0L
    override fun onBackPressed() {
        if (this::navController.isInitialized && navController.previousBackStackEntry == null) {
            val now = System.currentTimeMillis()
            if (now - onFirstPress < 1000) {
                super.onBackPressed()
            } else {
                Toast.makeText(this, "Press again to exit", Toast.LENGTH_SHORT).show()
                onFirstPress = now
            }
        } else {
            super.onBackPressed()
        }
    }

}