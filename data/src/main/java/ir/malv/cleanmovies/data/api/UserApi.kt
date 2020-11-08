package ir.malv.cleanmovies.data.api

import ir.malv.cleanmovies.data.model.user.TokenResponse
import ir.malv.cleanmovies.data.model.user.UserResponse
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query

interface UserApi {

    @POST("/api/v1/register")
    suspend fun registerUser(
      @Query("email") email: String,
      @Query("password") password: String,
      @Query("name") name: String
    ): UserResponse


    @POST("/oauth/token?grant_type=\"password\"")
    suspend fun applyForToken(
      @Query("username") userName: String,
      @Query("password") password: String
    ): TokenResponse


    @POST("/oauth/token?grant_type=\"refresh_token\"")
    suspend fun applyForToken(
      @Query("refresh_token") refreshToken: String
    ): TokenResponse


    @POST("/api/user")
    suspend fun getUserInfo(
      @Header("authorization") bearerToken: String,
      @Header("accept") responseType: String = "application/json"
    ): UserResponse
}