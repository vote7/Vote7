package agh.vote7.data

import agh.vote7.data.model.*
import kotlinx.coroutines.Deferred
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface RestApi {
    @POST("users/login")
    fun login(@Body request: LoginRequest): Deferred<TokenResponse>

    @POST("users/register")
    fun register(@Body request: RegisterRequest): Deferred<TokenResponse>

    @GET("users/logout")
    fun logout(): Deferred<Any>

    @GET("users/me")
    fun getCurrentUser(): Deferred<User>

    @GET("users/{userId}/groups")
    fun getUserGroups(@Path("userId") userId: UserId): Deferred<List<Group>>

    @GET("users/{userId}/polls")
    fun getUserPolls(@Path("userId") userId: UserId): Deferred<List<Poll>>

    @GET("polls/{pollId}")
    fun getPoll(@Path("pollId") pollId: PollId): Deferred<Poll>

    @GET("polls/{pollId}/question")
    fun getPollQuestions(@Path("pollId") pollId: PollId): Deferred<List<Question>>
}
