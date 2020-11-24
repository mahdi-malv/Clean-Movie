package ir.malv.cleanmovies.ui.screens.main

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ir.malv.cleanmovies.domain.entity.Genre
import ir.malv.cleanmovies.domain.entity.Movie
import ir.malv.cleanmovies.domain.entity.MovieSequence
import ir.malv.cleanmovies.domain.repository.MovieRepository
import ir.malv.cleanmovies.response.Response
import ir.malv.cleanmovies.utils.ColorUtil
import kotlinx.coroutines.launch
import java.lang.Exception

class MainViewModel @ViewModelInject constructor(
    private val movieRepository: MovieRepository
) : ViewModel() {

    val top25Movies: MutableState<Response<MovieSequence>> = mutableStateOf(Response.IDLE())
    val genres: MutableState<Response<List<Pair<Genre, Int>>>>
    = mutableStateOf(Response.IDLE())

    val movieDetail = mutableStateOf<Response<Movie>>(Response.IDLE())

    private val movieSequences: MutableState<MutableMap<Int, Response<MovieSequence>>> = mutableStateOf(mutableMapOf(0 to Response.IDLE()))
    private val genreMovieSequences: MutableState<MutableMap<Int, Response<MovieSequence>>> = mutableStateOf(mutableMapOf(0 to Response.IDLE()))

    val movies: MutableState<Response<MutableList<Movie>>> = mutableStateOf(Response.IDLE())
    val genreMovies: MutableState<Response<MutableList<Movie>>> = mutableStateOf(Response.IDLE())


    fun getTop25(forceRefresh: Boolean = false) = viewModelScope.launch {
        if (top25Movies.value !is Response.SUCCESS || forceRefresh) {
            top25Movies.value = Response.LOADING()
            top25Movies.value = try {
                Response.SUCCESS(movieRepository.getMovies())
            } catch (e: Exception) {
                Response.FAIL(e)
            }
        }
    }

    fun getGenres(forceRefresh: Boolean = false) = viewModelScope.launch {
        if (genres.value !is Response.SUCCESS<*> || forceRefresh) {
            genres.value = Response.LOADING()
            genres.value = try {
                Response.SUCCESS(
                    movieRepository.getGenres().map { it to ColorUtil.randomColor() }
                )
            } catch (e: Exception) {
                Response.FAIL(e)
            }
        }
    }

    fun getMovieDetail(movieId: Int) = viewModelScope.launch {
        movieDetail.value = Response.LOADING()
        movieDetail.value = try {
            Response.SUCCESS(movieRepository.getMovie(movieId))
        } catch (e: Exception) {
            Response.FAIL(e)
        }
    }

    fun getMovies() = viewModelScope.launch {
        val v = movieSequences.value.values
        val k = movieSequences.value.keys
        val page =
            if (v.isEmpty() || v.none { it is Response.SUCCESS }) { 1 }
            else { k.maxOrNull()?.let { it + 1 } ?: 1 }
        movieSequences.value[page] = Response.LOADING()
        movieSequences.value[page] = try {
            val s = movieRepository.getMovies(page)
            movies.value = movies.value.appendMovies(s.movies)
            Response.SUCCESS(s)
        } catch (e: Exception) {
            Response.FAIL(e)
        }
    }

    fun getGenreMovies(genreId: Int) = viewModelScope.launch {
        val v = genreMovieSequences.value.values
        val k = genreMovieSequences.value.keys
        val page =
            if (v.isEmpty() || v.none { it is Response.SUCCESS }) { 1 }
            else { k.maxOrNull()?.let { it + 1 } ?: 1 }
        genreMovieSequences.value[page] = Response.LOADING()
        genreMovieSequences.value[page] = try {
            val s = movieRepository.getGenreMovies(genreId = genreId, page)
            genreMovies.value = genreMovies.value.appendMovies(s.movies)
            Response.SUCCESS(s)
        } catch (e: Exception) {
            Response.FAIL(e)
        }
    }

    private fun Response<MutableList<Movie>>.appendMovies(data: List<Movie>): Response<MutableList<Movie>> {
        return if (this is Response.SUCCESS) {
            val a = mutableListOf<Movie>()
            a.addAll(this.data)
            a.addAll(data)
            a.distinctBy { it.id }
            Response.SUCCESS(a)
        } else {
            Response.SUCCESS(data.toMutableList())
        }
    }

}
