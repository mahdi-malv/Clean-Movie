package ir.malv.cleanmovies.domain.entity

data class Movie(
  val actors: String? = null,
  val director: String? = null,
  val genres: List<String>? = null,
  val id: Int? = null,
  val images: List<String>? = null,
  val metaScore: String? = null,
  val title: String? = null,
  val type: String? = null,
  val year: String? = null
)