package ir.malv.cleanmovies.domain.repository

import ir.malv.cleanmovies.domain.entity.Genre
import ir.malv.cleanmovies.domain.entity.Movie
import ir.malv.cleanmovies.domain.entity.MovieSequence

interface MovieRepository {
    suspend fun getMovies(pageNumber: Int = 0): MovieSequence
    suspend fun getMovie(movieId: Int): Movie
    suspend fun insertMovie(title: String, imdbId: String, country: String, year: Int, director: String? = null, imdbRating: String? = null, imdbVotes: String? = null, poster: String? = null)
    suspend fun getGenres(): List<Genre>
    suspend fun getGenreMovies(genreId: Int, pageNumber: Int = 0): MovieSequence
}