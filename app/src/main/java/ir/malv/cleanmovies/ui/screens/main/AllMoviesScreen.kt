package ir.malv.cleanmovies.ui.screens.main

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumnFor
import androidx.compose.foundation.lazy.LazyColumnForIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Error
import androidx.compose.runtime.Composable
import androidx.compose.runtime.onActive
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
import java.util.*

@Composable
fun AllMoviesScreen(
    viewModel: MainViewModel,
    genreName: String = "",
    genreId: Int = -1,
    onMovieClicked: (Int) -> Unit
) {
    if (genreName.isEmpty() || genreId == -1) {
        remember { viewModel.getMovies() }
    } else {
        remember(genreName) { viewModel.getGenreMovies(genreId) }
    }

    val response = if (genreName.isEmpty() || genreId == -1) {
        remember { viewModel.movies }
    } else {
        remember(genreName) { viewModel.genreMovies }
    }

    when (val s = response.value) {
        is Response.SUCCESS -> {
            SuccessUi(
                movies = s.data,
                onMovieClicked = onMovieClicked,
                onLoadMore = { fetchMovies(viewModel, genreName, genreId) }
            )
        }
        is Response.LOADING -> {
            // first loading
            LoadingUi()
        }
        is Response.FAIL<*, *> -> {
            // fail ui
            FailedUi()
        }
        else -> {
            // apparently nothing... Request again
            fetchMovies(viewModel, genreName, genreId)
        }
    }
}

private fun fetchMovies(
    viewModel: MainViewModel,
    genreName: String,
    genreId: Int
) {
    if (genreName.isEmpty() || genreId == -1)
        viewModel.getMovies()
    else viewModel.getGenreMovies(genreId)
}

@Composable
private fun SuccessUi(
    movies: List<Movie>,
    onLoadMore: () -> Unit = {},
    onMovieClicked: (Int) -> Unit
) = LazyColumnForIndexed(

    items = movies
) { index, movie ->
    MovieItem(
        movie = movie,
        modifier = Modifier
            .clickable(onClick = { onMovieClicked(movie.id) })
    )
    if (movies.lastIndex == index) {
        onActive {
            onLoadMore()
        }
    }
}

@Composable
private fun MovieItem(movie: Movie, modifier: Modifier = Modifier) = Card(
    modifier = Modifier.fillMaxWidth().height(120.dp).padding(4.dp),
    elevation = 4.dp,
    shape = RoundedCornerShape(5.dp)
) {
    Row(
        modifier = modifier.fillMaxSize(),

        ) {
        Card(
            modifier = Modifier.fillMaxHeight().width(110.dp).padding(2.dp),
            shape = RoundedCornerShape(2.dp),
            elevation = 1.dp
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
                style = TextStyle(fontSize = 19.sp),
                overflow = TextOverflow.Ellipsis,
                maxLines = 1
            )
            Spacer(modifier = Modifier.height(5.dp))
            Divider()
            Spacer(modifier = Modifier.height(5.dp))
            Text(
                movie.genres?.joinToString(", ") ?: "-",
                style = TextStyle(fontSize = 16.sp),
                overflow = TextOverflow.Ellipsis,
                maxLines = 1
            )
        }
    }
}

@Composable
private fun LoadingUi() = Loading()

@Composable
private fun FailedUi(/* Could use exception too */) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(Icons.Default.Error, tint = Color.Red)
        Spacer(modifier = Modifier.height(20.dp))
        Text(text = "Failed to fetch movies")
    }
}