package agh.vote7.data.model

typealias PollId = Long

data class Poll(
    val id: PollId,
    val name: String,
    val description: String?,
    val status: PollStatus
)
