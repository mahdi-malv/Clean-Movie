package ir.malv.cleanmovies.utils

object Validator {
    fun validateName(name: String) = name.length >= 5
    fun validateEmail(email: String) = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+".toRegex().matches(email)
    fun validatesPassword(password: String) = password.length >= 6

}