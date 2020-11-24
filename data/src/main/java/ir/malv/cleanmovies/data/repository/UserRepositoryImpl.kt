package ir.malv.cleanmovies.data.repository

import android.util.Log
import ir.malv.cleanmovies.data.api.UserApi
import ir.malv.cleanmovies.data.gen.UserStore
import ir.malv.cleanmovies.data.mapper.UserMapper
import ir.malv.cleanmovies.data.model.user.TokenResponse
import ir.malv.cleanmovies.data.model.user.UserResponse
import ir.malv.cleanmovies.data.store.UserStorage
import ir.malv.cleanmovies.domain.entity.User
import ir.malv.cleanmovies.domain.repository.UserRepository

class UserRepositoryImpl(
    private val userApi: UserApi,
    private val userMapper: UserMapper,
    private val userStorage: UserStorage
) : UserRepository {

    private var currentUser: User? = null

    override suspend fun currentUser(): User? {
        if (currentUser != null) return currentUser
        return userMapper.mapFrom(userStorage.get()).takeIf { it.email.isNotEmpty() }
    }

    override suspend fun login(email: String, password: String): User {
        val token = userApi.applyForToken(userName = email, password = password)
        val userResponse = userApi.getUserInfo("Bearer ${token.accessToken}")
        val user = storeToken(token, userResponse)
        return user.also { currentUser = it }
    }

    override suspend fun register(email: String, password: String, name: String): User {
        val userResponse = userApi.registerUser(email = email, password = password, name = name)
        val token = userApi.applyForToken(email, password)
        val user = storeToken(token, userResponse)
        return user.also { currentUser = it }
    }

    private suspend fun storeToken(
      token: TokenResponse,
      user: UserResponse
    ): User {
        val userStore = UserStore.newBuilder()
            .setId(user.id)
            .setEmail(user.email)
            .setToken(
                UserStore.TokenStore.newBuilder()
                    .setAccessToken(token.accessToken)
                    .setRefreshToken(token.refreshToken)
                    .setExpireDate(token.expiresIn)
                    .build()
            )
            .build()
        userStorage.set(userStore)
        return userMapper.mapFrom(userStore)
    }


}