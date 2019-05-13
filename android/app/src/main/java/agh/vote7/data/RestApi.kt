package agh.vote7.data

import agh.vote7.data.model.Group
import agh.vote7.data.model.User
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Path

interface RestApi {
    @GET("users/me")
    fun getCurrentUser(): Deferred<User>

    @GET("users/{userId}/groups")
    fun getUserGroups(@Path("userId") userId: String): Deferred<List<Group>>
}