package ir.malv.cleanmovies.domain.common

import ir.malv.cleanmovies.domain.exception.CleanMovieException

sealed class BaseResponse {
    class Success<R>(data: R)
    class Error<T>(data: T) where T : CleanMovieException
}