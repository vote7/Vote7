package agh.vote7.login.data.login

import agh.vote7.data.RestApi
import agh.vote7.data.TokenRepository
import agh.vote7.data.model.LoginRequest
import agh.vote7.login.data.Result

class LoginService(
    private val restApi: RestApi,
    private val tokenRepository: TokenRepository
) {
    suspend fun logout() {
        runCatching {
            restApi.logout().await()
        }
        tokenRepository.token = null
    }

    suspend fun login(email: String, password: String): Result<Unit> =
        try {
            val response = restApi.login(LoginRequest(email, password)).await()
            tokenRepository.token = response.token

            Result.Success(Unit)
        } catch (e: Exception) {
            Result.Error(e)
        }

    val isLoggedIn: Boolean
        get() = tokenRepository.token != null
}
