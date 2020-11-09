package ir.malv.cleanmovies.domain.repository

import ir.malv.cleanmovies.domain.entity.User

interface UserRepository {
    suspend fun currentUser(): User?
    suspend fun login(email: String, password: String): User
    suspend fun register(email: String, password: String, name: String): User
}