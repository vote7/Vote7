package agh.vote7.data.model

typealias UserId = Long

data class User(
    val id: UserId,
    val email: String,
    val name: String,
    val surname: String
)