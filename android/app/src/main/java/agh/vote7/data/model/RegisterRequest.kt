package agh.vote7.data.model

data class RegisterRequest(
    val name: String,
    val surname: String,
    val email: String,
    val password: String
)
