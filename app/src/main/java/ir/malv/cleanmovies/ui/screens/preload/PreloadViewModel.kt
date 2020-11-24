package ir.malv.cleanmovies.ui.screens.preload

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ir.malv.cleanmovies.domain.entity.User
import ir.malv.cleanmovies.domain.repository.UserRepository
import ir.malv.cleanmovies.response.Response
import kotlinx.coroutines.launch
import retrofit2.HttpException

class PreloadViewModel @ViewModelInject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    var currentUserState: Response<User> by mutableStateOf(Response.IDLE())

    suspend fun fetchUserInstance(): Response<User> {
        currentUserState = Response.LOADING()
        val currentUser = userRepository.currentUser()
        currentUserState = if (currentUser == null) {
            Response.EMPTY()
        } else {
            Response.SUCCESS(currentUser)
        }
        return currentUserState
    }

    fun login(email: String, password: String) = viewModelScope.launch {
        currentUserState = Response.LOADING()
        currentUserState = try {
            Response.SUCCESS(userRepository.login(email, password))
        } catch (e: HttpException) {
            val code = e.code()
            Response.FAIL(e, code)
        } catch (e: Exception) {
            Response.FAIL(e)
        }
    }

    fun register(email: String, password: String, name: String) = viewModelScope.launch {
        currentUserState = Response.LOADING()
        currentUserState = try {
            Response.SUCCESS(userRepository.register(email, password, name))
        } catch (e: HttpException) {
            val code = e.code()
            Response.FAIL(e, code)
        } catch (e: Exception) {
            Response.FAIL(e)
        }
    }
}