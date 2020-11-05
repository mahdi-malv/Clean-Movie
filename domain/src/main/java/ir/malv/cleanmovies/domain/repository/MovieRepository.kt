package ir.malv.cleanmovies.domain.repository

import ir.malv.cleanmovies.domain.entity.Movie

interface MovieRepository {
    suspend fun getMovies(): List<Movie>
    // TODO: We need more APIs and use cases
}