package ir.malv.cleanmovies.domain.entity

data class User(
  val id: Int,
  val email: String,
  val token: Token
)

data class Token(
  val accessToken: String,
  val refreshToken: String,
  val tokenExpireDate: Long
)