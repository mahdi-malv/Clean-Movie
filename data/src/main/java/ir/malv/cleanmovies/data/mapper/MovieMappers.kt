package ir.malv.cleanmovies.data.mapper

import ir.malv.cleanmovies.data.model.movie.GenreResponse
import ir.malv.cleanmovies.data.model.movie.MovieDetailResponse
import ir.malv.cleanmovies.data.model.movie.MovieDigestResponse
import ir.malv.cleanmovies.data.model.movie.MovieListResponse
import ir.malv.cleanmovies.domain.common.Mapper
import ir.malv.cleanmovies.domain.entity.Genre
import ir.malv.cleanmovies.domain.entity.Movie
import ir.malv.cleanmovies.domain.entity.MovieSequence
import ir.malv.cleanmovies.domain.entity.PageData

class DigestMovieMapper : Mapper<MovieDigestResponse, Movie> {
    override fun mapFrom(from: MovieDigestResponse): Movie {
        return Movie(
          title = from.title,
          id = from.id,
          genres = from.genres,
          images = from.images
        )
    }
}

class DetailedMovieMapper : Mapper<MovieDetailResponse, Movie> {
    override fun mapFrom(from: MovieDetailResponse): Movie {
        return Movie(
          title = from.title,
          id = from.id,
          genres = from.genres,
          images = from.images,
          director = from.director,
          actors = from.actors,
          type = from.type,
          metaScore = from.metascore,
          year = from.year
        )
    }
}

class MovieSequenceMapper(
  private val movieMapper: DigestMovieMapper
) : Mapper<MovieListResponse, MovieSequence> {
    override fun mapFrom(from: MovieListResponse): MovieSequence {
        return MovieSequence(
          from.movieDigests?.let { it.map { m -> movieMapper.mapFrom(m) } } ?: emptyList(),
          PageData(
            pageNumber = from.metadata.currentPage ?: 0,
            pages = from.metadata.pageCount ?: 0,
            totalCount = from.metadata.totalCount ?: 0,
            perPage = from.metadata.perPage ?: 0
          )
        )
    }

}

class GenreMapper : Mapper<GenreResponse, Genre> {
    override fun mapFrom(from: GenreResponse): Genre {
        return Genre(from.id, from.name)
    }

}