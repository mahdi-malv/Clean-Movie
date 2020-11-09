package ir.malv.cleanmovies.di

import android.content.Context
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
    fun providesOkHttp(logger: HttpLoggingInterceptor) = OkHttpClient.Builder()
        .addInterceptor { chain ->
            val originalRequest = chain.request()
            val requestWithHeaders = originalRequest.newBuilder()
                .header("Accept", "application/vnd.github.v3+json")
                .method(originalRequest.method, originalRequest.body)
                .build()
            chain.proceed(requestWithHeaders)
        }
        .addInterceptor(logger)
        .build()

    @Provides
    fun providesLoggingInterceptor() =
        HttpLoggingInterceptor().also { it.level = HttpLoggingInterceptor.Level.BASIC }


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
    fun providesUserRepo(userApi: UserApi, appDatabase: AppDatabase): UserRepository {
        return UserRepositoryImpl(
            userApi = userApi,
            userMapper = UserMapper(),
            appDatabase = appDatabase
        )
    }

    @Provides
    fun providesAppDatabase(@ApplicationContext context: Context) = AppDatabase.getInstance(context)
}