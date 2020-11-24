package ir.malv.cleanmovies.ui.utils

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle

object UiConstants {

    @Composable
    fun nightSensitiveColorStyle() = TextStyle(color = if (isSystemInDarkTheme()) Color.White else Color.Black)

}