package agh.vote7.data

import agh.vote7.data.model.Poll
import agh.vote7.data.model.PollId
import agh.vote7.data.model.Question

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
}