package ir.malv.cleanmovies.ui.screens.main

import androidx.compose.foundation.ScrollableColumn
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.lazy.LazyRowFor
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.ripple.AmbientRippleTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.chrisbanes.accompanist.coil.CoilImage
import ir.malv.cleanmovies.domain.entity.Movie
import ir.malv.cleanmovies.response.Response
import ir.malv.cleanmovies.ui.utils.Center
import ir.malv.cleanmovies.ui.utils.Loading
import ir.malv.cleanmovies.utils.ColorUtil
import java.lang.Exception

@Composable
fun MovieDetailScreen(
    viewModel: MainViewModel,
    movieId: Int
) {
    remember(movieId) { viewModel.getMovieDetail(movieId) }
    when (val s = viewModel.movieDetail.value) {
        is Response.IDLE, is Response.EMPTY -> {
            viewModel.getMovieDetail(movieId)
        }
        is Response.LOADING -> {
            MovieDetailLoading()
        }
        is Response.SUCCESS -> {
            MovieDetailSuccessUi(s.data)
        }
        is Response.FAIL<*, *> -> {
            MovieDetailFail(e = s.error)
        }
    }
}


// region SUCCESS
@Composable
private fun MovieDetailSuccessUi(movie: Movie) {
    ScrollableColumn(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth().padding(4.dp)
    ) {
        Spacer(modifier = Modifier.height(10.dp))
        MovieInfo(movie = movie, modifier = Modifier.height(250.dp))
        movie.images?.let { ScreenShots(images = it, modifier = Modifier.height(200.dp)) }
        movie.plot?.let { Plot(plot = it, modifier = Modifier.height(70.dp)) }
        movie.actors?.let { Actors(actors = it, modifier = Modifier.height(60.dp)) }
        Spacer(modifier = Modifier.height(56.dp))
    }

}

@Composable
private fun MovieInfo(movie: Movie, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        Card(
            modifier = Modifier.fillMaxHeight().width(150.dp).padding(8.dp),
            shape = RoundedCornerShape(10.dp),
            elevation = 5.dp
        ) {
            val color = remember { ColorUtil.randomColor() }
            val poster = movie.poster
            if (poster == null) {
                Box(modifier = Modifier.background(Color(color)))
            } else {
                CoilImage(data = poster)
            }
        }
        Spacer(modifier = Modifier.width(10.dp))
        Column(
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = movie.title ?: "[Unknown title]",
                style = TextStyle(fontSize = 22.sp),
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(5.dp))
            Text(
                "Year: ${movie.year ?: "-"}",
                style = TextStyle(fontSize = 13.sp),
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(5.dp))
            Divider()
            Spacer(modifier = Modifier.height(5.dp))
            Text(
                "Director: ${movie.director ?: "-"}",
                style = TextStyle(fontSize = 13.sp),
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(5.dp))
            Divider()
            Spacer(modifier = Modifier.height(5.dp))
            Text(
                movie.genres?.joinToString(", ") ?: "-",
                style = TextStyle(fontSize = 16.sp),
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Composable
private fun ScreenShots(images: List<String>, modifier: Modifier = Modifier) = Card(
    modifier = modifier.fillMaxWidth()
) {
    LazyRowFor(items = images) {
        Card(modifier = Modifier.fillMaxHeight().padding(8.dp), shape = RoundedCornerShape(4.dp)) {
            CoilImage(data = it)
        }
    }
}

@Composable
private fun Plot(plot: String, modifier: Modifier = Modifier) = Card(
    modifier = modifier.fillMaxWidth().padding(4.dp)
) {
    ScrollableColumn(modifier = Modifier.fillMaxSize()) {
        Text(text = plot, overflow = TextOverflow.Ellipsis)
    }
}

@Composable
private fun Actors(actors: String, modifier: Modifier = Modifier) = Card(
    modifier = modifier.fillMaxWidth().padding(4.dp)
) {
    Text(text = "Actors:  $actors")
}
// endregion

// region FAIL
@Composable
private fun MovieDetailFail(e: Throwable) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(Icons.Default.Error, tint = Color.Red)
        Spacer(modifier = Modifier.height(20.dp))
        Text(text = "Failed to get movie information")
    }
}
// endregion

// region LOADING
@Composable
private fun MovieDetailLoading() {
    Loading()
}
//endregion