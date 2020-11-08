package ir.malv.cleanmovies.data.model.user


import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
@Entity(tableName = "user_info")
data class UserResponse(


    @Json(name = "created_at")
    @ColumnInfo(name = "created_at")
    val createdAt: String? = null,


    @Json(name = "email")
    @ColumnInfo(name = "email")
    @PrimaryKey
    val email: String,


    @Json(name = "id")
    @ColumnInfo(name = "id")
    val id: Int,


    @Json(name = "name")
    @ColumnInfo(name = "name")
    val name: String?,


    @Json(name = "updated_at")
    @ColumnInfo(name = "updated_at")
    val updatedAt: String? = null
)