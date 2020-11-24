package ir.malv.cleanmovies.ui.utils

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ErrorOutline
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun Center(
    modifier: Modifier = Modifier,
    child: @Composable () -> Unit
) {
    Box(
        alignment = Alignment.Center,
        modifier = modifier.fillMaxSize()
    ) {
        child()
    }
}

@Composable
fun Error(
    modifier: Modifier = Modifier,
    message: String = ""
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(Icons.Default.ErrorOutline, modifier = modifier.size(80.dp))
        if (message.isNotBlank()) {
            Text(text = message, modifier = Modifier.align(Alignment.CenterHorizontally))
        }
    }
}

@Composable
fun Loading() {
    CleanMoviesTheme {
        Surface(color = MaterialTheme.colors.background) {
            Center { CircularProgressIndicator() }
        }
    }
}

@Composable
fun Failed(message: String) {
    CleanMoviesTheme {
        Surface(color = MaterialTheme.colors.background) {
            Error(message = message)
        }
    }
}