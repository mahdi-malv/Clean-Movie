package ir.malv.cleanmovies.data.model.movie
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MovieDetailResponse(
    @Json(name = "actors")
    val actors: String? = null,
    @Json(name = "awards")
    val awards: String? = null,
    @Json(name = "country")
    val country: String? = null,
    @Json(name = "director")
    val director: String? = null,
    @Json(name = "genres")
    val genres: List<String>? = null,
    @Json(name = "id")
    val id: Int,
    @Json(name = "images")
    val images: List<String>? = null,
    @Json(name = "imdb_id")
    val imdbId: String? = null,
    @Json(name = "imdb_rating")
    val imdbRating: String? = null,
    @Json(name = "imdb_votes")
    val imdbVotes: String? = null,
    @Json(name = "metascore")
    val metascore: String? = null,
    @Json(name = "plot")
    val plot: String? = null,
    @Json(name = "poster")
    val poster: String? = null,
    @Json(name = "rated")
    val rated: String? = null,
    @Json(name = "released")
    val released: String? = null,
    @Json(name = "runtime")
    val runtime: String? = null,
    @Json(name = "title")
    val title: String? = null,
    @Json(name = "type")
    val type: String? = null,
    @Json(name = "writer")
    val writer: String? = null,
    @Json(name = "year")
    val year: String? = null
)