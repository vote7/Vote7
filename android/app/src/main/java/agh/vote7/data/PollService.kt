package agh.vote7.data

import agh.vote7.data.model.*

class PollService(
    private val restApi: RestApi
) {
    suspend fun getOngoingPolls(): List<Poll> {
        val user = restApi.getCurrentUser().await()
        val polls = restApi.getUserPolls(user.id).await()
        return polls.filter { it.isOngoing }
    }

    suspend fun getPoll(pollId: PollId): Poll =
        restApi.getPoll(pollId).await()

    suspend fun getPollQuestions(pollId: PollId): List<Question> =
        restApi.getPollQuestions(pollId).await()
            .sortedWith(compareBy(Question::order, Question::id))
            .map { question ->
                question.copy(
                    answers = question.answers.sortedBy(Answer::content)
                )
            }

    suspend fun getPollVotes(pollId: PollId): Map<QuestionId, Answer> {
        val user = restApi.getCurrentUser().await()
        val polls = restApi.getUserPollsWithVotes(user.id).await()
        val poll = polls.first { it.poll.id == pollId }

        return poll.questions
            .filter { it.votes.isNotEmpty() }
            .map { it.question.id to it.votes.first() }
            .toMap()
    }

    suspend fun voteOnQuestion(questionId: QuestionId, answer: String) {
        // Workaround: There are separate endpoints for creating an answer and voting for it
        runCatching {
            restApi.addAnswerToQuestion(questionId, AddAnswerRequest(answer)).await()
        }
        restApi.voteOnQuestion(questionId, VoteOnQuestionRequest(answer)).await()
    }
}
