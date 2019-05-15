package agh.vote7.login.data.register

import agh.vote7.data.RestApi
import agh.vote7.data.TokenRepository
import agh.vote7.data.model.RegisterRequest
import agh.vote7.login.data.Result

class RegisterService(
    private val restApi: RestApi,
    private val tokenRepository: TokenRepository
) {
    suspend fun register(name: String, surname: String, email: String, password: String): Result<Unit> =
        try {
            val response = restApi.register(RegisterRequest(name, surname, email, password)).await()
            tokenRepository.token = response.token

            Result.Success(Unit)
        } catch (e: Exception) {
            Result.Error(e)
        }
}
