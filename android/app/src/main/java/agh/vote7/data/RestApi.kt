package agh.vote7.data

import kotlinx.coroutines.Deferred
import retrofit2.http.GET

interface RestApi {
    @GET("users/me")
    fun getUser(): Deferred<User>
}