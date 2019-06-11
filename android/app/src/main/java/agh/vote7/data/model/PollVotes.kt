package agh.vote7.data.model

import com.google.gson.annotations.SerializedName

data class PollVotes(
    val poll: Poll,
    @SerializedName("answeredQuestion") val questions: List<QuestionVotes>
)

data class QuestionVotes(
    @SerializedName("answers") val votes: List<Answer>,
    val question: Question
)
