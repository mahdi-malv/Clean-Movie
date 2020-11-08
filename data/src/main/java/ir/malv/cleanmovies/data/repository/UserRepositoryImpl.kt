package ir.malv.cleanmovies.data.repository

import ir.malv.cleanmovies.data.api.UserApi
import ir.malv.cleanmovies.data.db.AppDatabase
import ir.malv.cleanmovies.data.mapper.UserMapper
import ir.malv.cleanmovies.data.model.user.TokenResponse
import ir.malv.cleanmovies.data.model.user.UserResponse
import ir.malv.cleanmovies.domain.entity.User
import ir.malv.cleanmovies.domain.repository.UserRepository

class UserRepositoryImpl(
  private val userMapper: UserMapper,
  private val userApi: UserApi,
  private val appDatabase: AppDatabase
) : UserRepository {

    private var currentUser: User? = null

    override suspend fun currentUser(): User? {
        if (currentUser != null) return currentUser

        val savedUser = appDatabase.userDao().getUser() ?: return null
        var savedToken = appDatabase.tokenDao().getUserToken() ?: return null
        if (savedToken.expriesIn <= System.currentTimeMillis()) {
            savedToken = userApi.applyForToken(savedToken.refreshToken)
        }
        return userMapper.transfer(savedUser, savedToken)
    }

    override suspend fun login(email: String, password: String) {
        val token = userApi.applyForToken(userName = email, password = password)
        val user = userApi.getUserInfo("Bearer ${token.accessToken}")
        currentUser = storeToken(token, user)

    }

    override suspend fun register(email: String, password: String, name: String) {
        val user = userApi.registerUser(email = email, password = password, name = name)
        val token = userApi.applyForToken(email, password)
        currentUser = storeToken(token, user)
    }

    private suspend fun storeToken(
      token: TokenResponse,
      user: UserResponse
    ): User = appDatabase.let {
        it.userDao().insertUser(user)
        it.tokenDao().insertUserToken(token)
        userMapper.transfer(user, token)
    }


}