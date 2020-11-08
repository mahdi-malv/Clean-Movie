package ir.malv.cleanmovies.data.db.dao

import androidx.room.*
import ir.malv.cleanmovies.data.model.user.TokenResponse

@Dao
interface TokenDao {
    @Insert
    suspend fun insertUserToken(tokenResponse: TokenResponse)

    @Update
    suspend fun updateUserToken(tokenResponse: TokenResponse)

    @Query("select * from user_token limit 1")
    suspend fun getUserToken(): TokenResponse?

    @Delete
    suspend fun deleteToken(tokenResponse: TokenResponse)

    @Query("delete from user_token")
    suspend fun clearToken()
}