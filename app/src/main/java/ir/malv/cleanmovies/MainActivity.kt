package ir.malv.cleanmovies

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Text
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.setContent
import androidx.ui.tooling.preview.Preview
import ir.malv.cleanmovies.ui.CleanMoviesTheme

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            // TODO 1. Navigation - Specify routes
            // TODO 2. Pages - Think about pages and each one's design
            // TODO 3. Pages - Design
            // TODO 4. Make a main view model or idunno multiple ones to handle connection to data module
            // TODO 5. Dependencies - make sure everything exists


            CleanMoviesTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    Greeting("Android")
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    CleanMoviesTheme {
        Greeting("Android")
    }
}