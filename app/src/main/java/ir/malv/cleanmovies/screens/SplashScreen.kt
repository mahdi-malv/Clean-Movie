package ir.malv.cleanmovies.screens

import android.widget.Toast
import androidx.compose.foundation.Text
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigate
import ir.malv.cleanmovies.Greeting
import ir.malv.cleanmovies.ui.CleanMoviesTheme
import ir.malv.cleanmovies.ui.utils.Center
import ir.malv.cleanmovies.utils.CMScope
import ir.malv.cleanmovies.utils.cmScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
inline fun SplashScreen(crossinline onTimePassed: () -> Unit) {
    cmScope {
        delay(2000)
        withContext(Dispatchers.Main) {
            onTimePassed()
        }
    }

    Center {
        Text("Splashing...")
    }
}