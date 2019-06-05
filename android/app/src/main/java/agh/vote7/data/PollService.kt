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

    suspend fun getOngoingPolls(): List<Poll> =
        getPolls()
            .filter { it.isOngoing }

    suspend fun getPollQuestions(pollId: PollId): List<Question> =
        restApi.getPollQuestions(pollId).await()
            .sortedBy { it.order }

    suspend fun voteOnQuestion(questionId: QuestionId, answer: String) {
        // Workaround: There are separate endpoints for creating an answer and voting for it
        runCatching {
            restApi.addAnswerToQuestion(questionId, AddAnswerRequest(answer))
        }
        restApi.voteOnQuestion(questionId, VoteOnQuestionRequest(answer)).await()
    }
}
