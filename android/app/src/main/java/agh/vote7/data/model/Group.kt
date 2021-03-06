package agh.vote7.data.model

typealias GroupId = Long

data class Group(
    val id: GroupId,
    val name: String,
    val description: String?
)