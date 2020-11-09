package ir.malv.cleanmovies

import androidx.compose.foundation.currentTextStyle
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ir.malv.cleanmovies.domain.entity.User
import ir.malv.cleanmovies.domain.exception.CleanMovieException
import ir.malv.cleanmovies.domain.repository.MovieRepository
import ir.malv.cleanmovies.domain.repository.UserRepository
import ir.malv.cleanmovies.exception.UserNotFoundException
import ir.malv.cleanmovies.response.Response
import kotlinx.coroutines.launch

class MainViewModel @ViewModelInject constructor(
    private val userRepository: UserRepository,
    private val movieRepository: MovieRepository
) : ViewModel() {

    var currentUserState: Response<User> by mutableStateOf(Response.IDLE())

    fun fetchUserInstance() = viewModelScope.launch {
        currentUserState = Response.LOADING()
        val currentUser = userRepository.currentUser()
        currentUserState = if (currentUser == null) {
            Response.Fail(CleanMovieException(("User was not cached")))
        } else {
            Response.Success(currentUser)
        }
    }

    fun login(email: String, password: String) = viewModelScope.launch {
        currentUserState = Response.LOADING()
        currentUserState = try {
            Response.Success(userRepository.login(email, password))
        } catch (e: Exception) {
            Response.Fail(CleanMovieException("Failed to login"))
        }
    }

    fun register(email: String, password: String, name: String) = viewModelScope.launch {
        currentUserState = Response.LOADING()
        currentUserState = try {
            Response.Success(userRepository.register(email, password, name))
        } catch (e: Exception) {
            Response.Fail(CleanMovieException("Failed to register")) // TODO: Error codes must be specific
        }
    }
}
