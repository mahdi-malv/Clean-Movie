package ir.malv.cleanmovies.data.mapper

import ir.malv.cleanmovies.data.gen.UserStore
import ir.malv.cleanmovies.data.model.user.TokenResponse
import ir.malv.cleanmovies.data.model.user.UserResponse
import ir.malv.cleanmovies.domain.common.Aggregator
import ir.malv.cleanmovies.domain.common.Mapper
import ir.malv.cleanmovies.domain.entity.Token
import ir.malv.cleanmovies.domain.entity.User

class UserAggregator : Aggregator<UserResponse, TokenResponse, User> {
    override fun transfer(first: UserResponse, second: TokenResponse): User {
        return User(
          id = first.id,
          email = first.email,
          token = Token(
            accessToken = second.accessToken,
            refreshToken = second.refreshToken,
            tokenExpireDate = second.expiresIn
          )
        )
    }
}

class UserMapper : Mapper<UserStore, User> {
    override fun mapFrom(from: UserStore): User {
        return User(
            id = from.id,
            email = from.email,
            token = Token(
                accessToken = from.token.accessToken,
                refreshToken = from.token.refreshToken,
                tokenExpireDate = from.token.expireDate
            )
        )
    }

}