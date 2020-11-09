package ir.malv.cleanmovies.exception

import ir.malv.cleanmovies.domain.exception.CleanMovieException

class UserNotFoundException(): CleanMovieException("User was not found")
