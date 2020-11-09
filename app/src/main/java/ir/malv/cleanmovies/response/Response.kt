package ir.malv.cleanmovies.response

import ir.malv.cleanmovies.domain.exception.CleanMovieException

sealed class Response<T>(data: T?) {
    class IDLE<T> : Response<T>(null)
    class LOADING<T>: Response<T>(null)
    class Success<T>(data: T): Response<T>(data)
    class Fail<T, R>(error: R): Response<T>(null) where R: CleanMovieException
}