package ir.malv.cleanmovies.data.repository

import ir.malv.cleanmovies.domain.entity.Movie
import ir.malv.cleanmovies.domain.exception.CleanMovieException
import ir.malv.cleanmovies.domain.repository.MovieRepository

class MoviesRespositoryImpl : MovieRepository {
    override suspend fun getMovies(): List<Movie> {
        throw CleanMovieException("Not implemented")
    }

}