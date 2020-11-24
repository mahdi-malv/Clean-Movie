package ir.malv.cleanmovies.di

import android.content.Context
import android.util.Log
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import ir.malv.cleanmovies.data.Constants
import ir.malv.cleanmovies.data.api.MoviesApi
import ir.malv.cleanmovies.data.api.UserApi
import ir.malv.cleanmovies.data.db.AppDatabase
import ir.malv.cleanmovies.data.enhanceByDataModule
import ir.malv.cleanmovies.data.mapper.*
import ir.malv.cleanmovies.data.repository.MoviesRepositoryImpl
import ir.malv.cleanmovies.data.repository.UserRepositoryImpl
import ir.malv.cleanmovies.data.store.UserStorage
import ir.malv.cleanmovies.domain.repository.MovieRepository
import ir.malv.cleanmovies.domain.repository.UserRepository
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

@Module
@InstallIn(ApplicationComponent::class)
object AppModule {

    @Provides
    fun providesRetrofit(okHttpClient: OkHttpClient, moshi: Moshi): Retrofit = Retrofit.Builder()
        .client(okHttpClient)
        .baseUrl(Constants.Network.BASE_URL)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .build()


    @Provides
    fun providesOkHttp() = OkHttpClient.Builder()
//        .addInterceptor { chain ->
//            val originalRequest = chain.request()
//            val requestWithHeaders = originalRequest.newBuilder()
//                .header("Accept", "application/json")
//                .method(originalRequest.method, originalRequest.body)
//                .build()
//            Log.d("App(Net)", """
//                Method: ${originalRequest.method}
//            """.trimIndent())
//            chain.proceed(requestWithHeaders)
//        }
        .addInterceptor(HttpLoggingInterceptor().also { it.setLevel(HttpLoggingInterceptor.Level.BODY) })
        .build()

    @Provides
    fun providersMoshi(): Moshi = Moshi.Builder()
        .enhanceByDataModule()
        .build()
}

@Module
@InstallIn(ApplicationComponent::class)
object RepoModule {

    @Provides
    fun providesUserApi(retrofit: Retrofit): UserApi = retrofit.create(UserApi::class.java)

    @Provides
    fun providesMoviesApi(retrofit: Retrofit): MoviesApi = retrofit.create(MoviesApi::class.java)

    @Provides
    fun providesMoviesRepo(moviesApi: MoviesApi): MovieRepository {
        return MoviesRepositoryImpl(
            moviesApi = moviesApi,
            detailedMovieMapper = DetailedMovieMapper(),
            movieSequenceMapper = MovieSequenceMapper(DigestMovieMapper()),
            genreMapper = GenreMapper()
        )
    }
    @Provides
    fun providesUserRepo(@ApplicationContext context: Context, userApi: UserApi, appDatabase: AppDatabase): UserRepository {
        return UserRepositoryImpl(
            userApi = userApi,
            userMapper = UserMapper(),
            userStorage = UserStorage(context)
        )
    }

    @Provides
    fun providesAppDatabase(@ApplicationContext context: Context) = AppDatabase.getInstance(context)
}