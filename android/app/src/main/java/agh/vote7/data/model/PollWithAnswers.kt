package agh.vote7.data.model

data class QuestionWithAnswer(
    val answers: List<Answer>,
    val question: Question
)

data class PollWithAnswers(
    val poll: Poll,
    val answeredQuestion: List<QuestionWithAnswer>
)
