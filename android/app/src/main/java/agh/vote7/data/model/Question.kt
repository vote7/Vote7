package agh.vote7.data.model

typealias QuestionId = Long

data class Question(
    val id: QuestionId,
    val content: String,
    val order: Int,
    val open: Boolean,
    val answers: List<Answer> = emptyList(),
    val status: QuestionStatus
)
