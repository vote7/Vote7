package agh.vote7.login.data.model

/**
 * Data class that captures user information for logged in users retrieved from LoginRepository
 */
data class LoggedInUser(
    val id: String,
    val name: String,
    val surname: String,
    val email: String
)
