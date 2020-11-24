package ir.malv.cleanmovies.domain.exception

open class CleanMovieException(message: String, t: Throwable = Throwable()) : Exception(message, t)