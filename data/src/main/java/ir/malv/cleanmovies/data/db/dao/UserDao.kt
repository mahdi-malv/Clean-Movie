package ir.malv.cleanmovies.data.db.dao

import androidx.room.*
import ir.malv.cleanmovies.data.model.user.TokenResponse
import ir.malv.cleanmovies.data.model.user.UserResponse

@Dao
interface UserDao {
    @Insert
    suspend fun insertUser(user: UserResponse)

    @Update
    suspend fun updateUser(user: UserResponse)

    @Query("select * from user_info limit 1")
    suspend fun getUser(): UserResponse?

    @Delete
    suspend fun deleteUserInfo(user: UserResponse)

    @Query("delete from user_info")
    suspend fun clearUserInfo()
}