package ir.malv.cleanmovies.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import ir.malv.cleanmovies.data.db.dao.TokenDao
import ir.malv.cleanmovies.data.db.dao.UserDao
import ir.malv.cleanmovies.data.model.user.TokenResponse
import ir.malv.cleanmovies.data.model.user.UserResponse

@Database(entities = [TokenResponse::class, UserResponse::class], exportSchema = false, version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao
    abstract fun tokenDao(): TokenDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        @Synchronized
        fun getInstance(context: Context) = INSTANCE ?: Room.databaseBuilder(
          context.applicationContext,
          AppDatabase::class.java,
          "user_cache.db"
        ).build().also { INSTANCE = it }
    }
}