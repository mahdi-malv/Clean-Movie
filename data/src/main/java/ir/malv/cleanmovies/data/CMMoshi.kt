package ir.malv.cleanmovies.data

import com.squareup.moshi.Moshi
import ir.malv.cleanmovies.data.model.movie.*
import ir.malv.cleanmovies.data.model.user.TokenResponse
import ir.malv.cleanmovies.data.model.user.TokenResponseJsonAdapter
import ir.malv.cleanmovies.data.model.user.UserResponse
import ir.malv.cleanmovies.data.model.user.UserResponseJsonAdapter

fun Moshi.Builder.enhanceByDataModule(): Moshi.Builder {
    add { type, _, moshi ->
        when(type) {
            GenreResponse::class.java -> GenreResponseJsonAdapter(moshi)
            InsertMovieResponse::class.java -> InsertMovieResponseJsonAdapter(moshi)
            MovieDetailResponse::class.java -> MovieDetailResponseJsonAdapter(moshi)
            MovieDigestResponse::class.java -> MovieDigestResponseJsonAdapter(moshi)
            Metadata::class.java -> MetadataJsonAdapter(moshi)
            MovieListResponse::class.java -> MovieListResponseJsonAdapter(moshi)
            TokenResponse::class.java -> TokenResponseJsonAdapter(moshi)
            UserResponse::class.java -> UserResponseJsonAdapter(moshi)
            else -> null
        }
    }
    return this
}