package ir.malv.cleanmovies.ui.utils

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable

private val DarkColorPalette = darkColors(
  primary = purple200,
  primaryVariant = purple700,
  secondary = teal200
)

private val LightColorPalette = lightColors(
  primary = purple500,
  primaryVariant = purple700,
  secondary = teal200

  /* Other default colors to override
    background = Color.White,
    surface = Color.White,
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Color.Black,
    onSurface = Color.Black,
    */
)

@Composable
fun CleanMoviesTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
      colors = colors,
      typography = typography,
      shapes = shapes,
      content = content
    )
}

@Composable
fun CleanMovieSurface(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    CleanMoviesTheme(darkTheme = darkTheme) {
        Surface(color = MaterialTheme.colors.background) {
            content()
        }
    }
}