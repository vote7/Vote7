package agh.vote7.data

import agh.vote7.data.model.Poll

class PollService(
    private val restApi: RestApi
) {
    suspend fun getPolls(): List<Poll> {
        val user = restApi.getCurrentUser().await()
        return restApi.getUserPolls(user.id).await()
    }
}