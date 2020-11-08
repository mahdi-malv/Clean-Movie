package ir.malv.cleanmovies.data.model.movie
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MovieDigestResponse(
    @Json(name = "genres")
    val genres: List<String>? = null,
    @Json(name = "id")
    val id: Int,
    @Json(name = "images")
    val images: List<String>? = null,
    @Json(name = "poster")
    val poster: String? = null,
    @Json(name = "title")
    val title: String
)

@JsonClass(generateAdapter = true)
data class MovieListResponse(
  @Json(name = "data")
  val  movieDigests: List<MovieDigestResponse>? = null,
  @Json(name = "metadata")
  val metadata: Metadata
)

@JsonClass(generateAdapter = true)
data class Metadata(
  @Json(name = "current_page")
  val currentPage: Int? = null,
  @Json(name = "page_count")
  val pageCount: Int? = null,
  @Json(name = "per_page")
  val perPage: Int? = null,
  @Json(name = "total_count")
  val totalCount: Int? = null
)