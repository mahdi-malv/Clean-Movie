package ir.malv.cleanmovies.data.store

import android.content.Context
import ir.malv.cleanmovies.data.gen.UserStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first

class UserStorage(private val context: Context) {
    suspend fun get(): UserStore = UserSerializer.get(context).data.first()

    suspend fun set(user: UserStore) {
        UserSerializer.get(context).updateData {
            user
        }
    }
}