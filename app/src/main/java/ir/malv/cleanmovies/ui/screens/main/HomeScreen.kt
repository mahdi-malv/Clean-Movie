package ir.malv.cleanmovies.ui.screens.main

import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollableColumn
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRowFor
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ErrorOutline
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.chrisbanes.accompanist.coil.CoilImage
import ir.malv.cleanmovies.R
import ir.malv.cleanmovies.domain.entity.Genre
import ir.malv.cleanmovies.domain.entity.Movie
import ir.malv.cleanmovies.response.Response
import ir.malv.cleanmovies.ui.utils.Center
import ir.malv.cleanmovies.ui.utils.Failed
import ir.malv.cleanmovies.utils.ColorUtil


@Composable
fun HomeScreen(
    viewModel: MainViewModel,
    onMovie: (Int)->Unit,
    onAllMovies: ()->Unit,
    onAllGenres: ()->Unit,
    onGenre: (String, Int)->Unit
) {
    remember { viewModel.getTop25() }
    remember { viewModel.getGenres() }
    val t10 = remember { viewModel.top25Movies }
    val gens = remember { viewModel.genres }
    ScrollableColumn(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start,
        modifier = Modifier.fillMaxSize().padding(8.dp)
    ) {
        Space()
        TitleText(text = "Recommended")
        Space()
        when (val s = t10.value) {
            is Response.LOADING -> {
                Box(modifier = Modifier.fillMaxWidth().height(140.dp)) {
                    Center { CircularProgressIndicator() }
                }
            }
            is Response.SUCCESS -> {
                LazyRowFor(items = s.data.movies.take(10)) { item ->
                    MovieItem(
                        movie = item,
                        modifier = Modifier.clickable(onClick = { onMovie(item.id) })
                    )
                }
            }
            else -> {
                Box(modifier = Modifier.fillMaxWidth().height(110.dp)) {
                    Center { Icon(Icons.Default.ErrorOutline, tint = Color.Red) }
                }
            }
        }
        Space()
        Text(
            text = "all >", modifier = Modifier
                .align(Alignment.End)
                .padding(4.dp)
                .clickable(onClick = onAllMovies)
        )
        Space(height = 30)
        Divider()
        Space(height = 30)
        TitleText(text = "Genre")
        Space()
        when (val s = gens.value) {
            is Response.LOADING -> {
                Box(modifier = Modifier.fillMaxWidth().height(110.dp)) {
                    Center { CircularProgressIndicator() }
                }
            }
            is Response.SUCCESS -> {
                LazyRowFor(items = s.data.take(10)) { item ->
                    GenreItem(
                        genre = item.first,
                        color = item.second,
                        modifier = Modifier.clickable(onClick = {
                            onGenre(item.first.name, item.first.id)
                        })
                    )
                }
                Space()
                Text(
                    text = "all >", modifier = Modifier
                        .align(Alignment.End)
                        .padding(4.dp)
                        .clickable(onClick = onAllGenres)
                )
            }
            else -> {
                Box(modifier = Modifier.fillMaxWidth().height(110.dp)) {
                    Failed(message = "Failed to fetch data")
                }
            }
        }
    }
}

@Composable
private fun MovieItem(
    movie: Movie,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = Modifier.width(230.dp).height(190.dp).padding(6.dp),
        elevation = 5.dp
    ) {
        Row(
            modifier = modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.Start
        ) {
            val poster = movie.poster
            if (poster != null) {
                CoilImage(data = poster)
            } else {
                Box(modifier = Modifier.fillMaxHeight().width(70.dp)) {
                    Center {
                        Image(imageResource(id = R.drawable.logo), contentScale = ContentScale.Inside)
                    }
                }
            }
            Column(
                verticalArrangement = Arrangement.SpaceAround,
                horizontalAlignment = Alignment.Start,
                modifier = Modifier.fillMaxSize().padding(2.dp)
            ) {
                Text(text = movie.title ?: "[Unknown title]", style = TextStyle(fontSize = 19.sp))
                Text(
                    text = movie.genres?.joinToString(separator = ", ") ?: "[Unknown Genre]",
                    style = TextStyle(fontSize = 12.sp)
                )
            }
        }
    }
}

@Composable
private fun GenreItem(
    genre: Genre,
    color: Int,
    modifier: Modifier = Modifier
) {
    val isColorDark = ColorUtil.isColorDark(color)

    Card(
        modifier = Modifier.width(140.dp).height(100.dp).padding(6.dp),
    ) {
        Box(modifier = modifier.fillMaxSize().background(Color(color))) {
            Text(
                text = genre.name, style = TextStyle(fontSize = 20.sp, color = if(isColorDark) Color.White else Color.Black),
                modifier = Modifier.align(Alignment.TopStart).padding(4.dp)
            )
        }
    }
}

@Composable
private fun TitleText(text: String) {
    Text(text = text, style = TextStyle(fontSize = 19.sp, fontWeight = FontWeight.Bold))
}

@Composable
private fun Space(height: Int = 20) {
    Spacer(modifier = Modifier.height(height.dp))
}