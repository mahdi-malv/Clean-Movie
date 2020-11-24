package ir.malv.cleanmovies.data.model.user


import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
@Entity(tableName = "user_token")
data class TokenResponse(

  @Json(name = "access_token")
  @ColumnInfo(name = "access_token")
  @PrimaryKey
  val accessToken: String,


  @Json(name = "expires_in")
  @ColumnInfo(name = "expires_in")
  val expiresIn: Long,


  @Json(name = "refresh_token")
  @ColumnInfo(name = "refresh_token")
  val refreshToken: String,

  @Json(name = "token_type")
  @ColumnInfo(name = "token_type")
  val tokenType: String? = null
)