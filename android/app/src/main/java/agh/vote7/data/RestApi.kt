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
    fun getUserGroups(@Path("userId") userId: String): Deferred<List<Group>>
}
