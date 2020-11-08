package ir.malv.cleanmovies.domain.entity

data class MovieSequence(
  val genres: List<Movie>,
  val pageData: PageData
)

data class PageData(
  val pageNumber: Int,
  val pages: Int,
  val totalCount: Int,
  val perPage: Int
)