package ir.malv.cleanmovies.domain.entity

data class Movie(
  val actors: String? = null,
  val director: String? = null,
  val genres: List<String>? = null,
  val id: Int,
  val images: List<String>? = null,
  val poster: String? = null,
  val metaScore: String? = null,
  val title: String? = null,
  val plot: String? = null,
  val type: String? = null,
  val year: String? = null
)