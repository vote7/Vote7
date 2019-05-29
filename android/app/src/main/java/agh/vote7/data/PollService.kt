package agh.vote7.data

import agh.vote7.data.model.*

class PollService(
    private val restApi: RestApi
) {
    suspend fun getPolls(): List<Poll> {
        val user = restApi.getCurrentUser().await()
        return restApi.getUserPolls(user.id).await()
    }

    suspend fun getPoll(pollId: PollId): Poll =
        restApi.getPoll(pollId).await()

    suspend fun getPollQuestions(pollId: PollId): List<Question> =
        restApi.getPollQuestions(pollId).await()
            .sortedBy { it.order }

    suspend fun voteOnQuestion(questionId: QuestionId, answer: String): Unit =
        restApi.voteOnQuestion(questionId, VoteOnQuestionRequest(answer)).await()
}