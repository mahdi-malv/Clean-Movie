package ir.malv.cleanmovies.data.repository

import ir.malv.cleanmovies.data.api.MoviesApi
import ir.malv.cleanmovies.data.mapper.DetailedMovieMapper
import ir.malv.cleanmovies.data.mapper.GenreMapper
import ir.malv.cleanmovies.data.mapper.MovieSequenceMapper
import ir.malv.cleanmovies.domain.entity.Genre
import ir.malv.cleanmovies.domain.entity.Movie
import ir.malv.cleanmovies.domain.entity.MovieSequence
import ir.malv.cleanmovies.domain.repository.MovieRepository

class MoviesRepositoryImpl(
  private val moviesApi: MoviesApi,
  private val detailedMovieMapper: DetailedMovieMapper,
  private val movieSequenceMapper: MovieSequenceMapper,
  private val genreMapper: GenreMapper
) : MovieRepository {
    override suspend fun getMovies(pageNumber: Int): MovieSequence {
        return movieSequenceMapper.mapFrom(moviesApi.getMoviesList(pageNumber))
    }

    override suspend fun getMovie(movieId: Int): Movie {
        return detailedMovieMapper.mapFrom(moviesApi.getMovieDetails(movieId))
    }

    override suspend fun insertMovie(title: String, imdbId: String, country: String, year: Int, director: String?, imdbRating: String?, imdbVotes: String?, poster: String?) {
        moviesApi.insertMovie(
          mapOf(
            "title" to title,
            "imdb_id" to imdbId,
            "country" to country,
            "year" to year,
            "director" to director,
            "imdb_rating" to imdbRating,
            "imdb_votes" to imdbVotes,
            "poster" to poster
          ).filterValues { it != null }.mapValues { it }
        )
    }

    override suspend fun getGenres(): List<Genre> {
        return moviesApi.getGenres().map { genreMapper.mapFrom(it) }
    }

    override suspend fun getGenreMovies(genreId: Int, pageNumber: Int): MovieSequence {
        return movieSequenceMapper.mapFrom(moviesApi.getGenreMovies(genreId, pageNumber))
    }

}