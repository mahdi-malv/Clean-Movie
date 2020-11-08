package ir.malv.cleanmovies.data.model.movie


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class InsertMovieResponse(
    @Json(name = "country")
    val country: String? = null,
    @Json(name = "director")
    val director: String? = null,
    @Json(name = "id")
    val id: Int? = null,
    @Json(name = "imdb_id")
    val imdbId: String? = null,
    @Json(name = "imdb_rating")
    val imdbRating: String? = null,
    @Json(name = "imdb_votes")
    val imdbVotes: String? = null,
    @Json(name = "poster")
    val poster: String? = null,
    @Json(name = "title")
    val title: String? = null,
    @Json(name = "year")
    val year: Int? = null
)