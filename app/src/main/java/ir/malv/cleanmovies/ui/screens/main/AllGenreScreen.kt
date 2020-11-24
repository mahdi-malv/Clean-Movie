package ir.malv.cleanmovies.ui.screens.main

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumnFor
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Error
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ir.malv.cleanmovies.domain.entity.Genre
import ir.malv.cleanmovies.response.Response
import ir.malv.cleanmovies.ui.utils.Center
import ir.malv.cleanmovies.ui.utils.Loading
import ir.malv.cleanmovies.utils.ColorUtil

@Composable
fun AllGenreScreen(
    viewModel: MainViewModel,
    onGenreSelected: (String, Int)->Unit
) {
    val genres: MutableState<Response<List<Pair<Genre, Int>>>> = remember { viewModel.genres }

    when (val s = genres.value) {
        is Response.IDLE -> {
            // Should not happen I guess... Unless this screen was rendered before home
            viewModel.getGenres()
        }
        is Response.LOADING -> {
            // Loading UI
            LoadingUi()
        }
        is Response.SUCCESS -> {
            // Load Successful ui
            SuccessUi(onGenreSelected = onGenreSelected, data = s.data)
        }
        is Response.FAIL<*, *> -> {
            // Load Failed Ui
            FailUi()
        }
    }
}

@Composable
private fun LoadingUi() = Loading()

@Composable
private fun FailUi() = Column(
    verticalArrangement = Arrangement.Center,
    horizontalAlignment = Alignment.CenterHorizontally
) {
    Icon(asset = Icons.Default.Error, tint = Color.Red, modifier = Modifier.size(40.dp))
}

@Composable
private fun SuccessUi(
    onGenreSelected: (String, Int) -> Unit,
    data: List<Pair<Genre, Int>>
) = LazyColumnFor(
    modifier = Modifier.fillMaxSize(),
    horizontalAlignment = Alignment.CenterHorizontally,
    items = data
) {
    Card(
        modifier = Modifier.fillMaxWidth().height(70.dp).padding(4.dp),
        elevation = 5.dp,
        backgroundColor = Color(it.second).copy(alpha = 0.4f)
    ) {
        Center(modifier = Modifier.clickable(onClick = {
            onGenreSelected(it.first.name, it.first.id)
        })) {
            Text(it.first.name, style = TextStyle(fontSize = 19.sp, color = if(ColorUtil.isColorDark(it.second)) Color.White else Color.Black))
        }
    }
}