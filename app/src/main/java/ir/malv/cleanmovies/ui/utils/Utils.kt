package ir.malv.cleanmovies.ui.utils

import ir.malv.cleanmovies.ui.CleanMoviesTheme

import android.content.Context
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import androidx.compose.foundation.Text
import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.ErrorOutline
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ContextAmbient
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.mukesh.MarkdownView

@Composable
inline fun Center(
    modifier: Modifier = Modifier,
    crossinline child: @Composable () -> Unit
) {
    Box(
        alignment = Alignment.Center,
        modifier = modifier
    ) {
        child()
    }
}

@Composable
fun MarkdownText(
    content: String,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier.padding(2.dp)) {
        AndroidView(viewBlock = ::MarkdownView, modifier = modifier) {
            it.setMarkDownText(content)
        }
    }
}

@Composable
fun Error(
    modifier: Modifier = Modifier,
    message: String = ""
) {
    Column (
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ){
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
