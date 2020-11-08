package ir.malv.cleanmovies.data.api

import ir.malv.cleanmovies.data.model.movie.GenreResponse
import ir.malv.cleanmovies.data.model.movie.MovieDetailResponse
import ir.malv.cleanmovies.data.model.movie.MovieListResponse
import ir.malv.cleanmovies.domain.entity.Genre
import retrofit2.http.*

interface MoviesApi {

    @GET("/api/v1/movies/{movie_id}")
    suspend fun getMovieDetails(@Path("movie_id") movieId: Int): MovieDetailResponse


    @GET("/api/v1/movies")
    suspend fun getMoviesList(
      @Query("page") pageNumber: Int = 0,
      @Query("name") movieName: String? = null
    ): MovieListResponse


    @POST("/api/v1/movies")
    suspend fun insertMovie(@QueryMap data: Map<String, Any>)


    @GET("/api/v1/genres")
    suspend fun getGenres(): List<GenreResponse>


    @GET("/api/v1/genres/{genre_id}/movies")
    suspend fun getGenreMovies(
      @Path("genre_id") genreId: Int,
      @Query("page") pageNumber: Int = 0
    ): MovieListResponse
}