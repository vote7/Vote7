package agh.vote7.utils

import agh.vote7.data.RestApi
import agh.vote7.data.RestApiFactory
import agh.vote7.data.TokenRepository
import agh.vote7.login.data.login.LoginService
import agh.vote7.login.data.register.RegisterService
import agh.vote7.login.ui.login.LoginViewModel
import agh.vote7.login.ui.register.RegisterViewModel
import agh.vote7.main.profile.ProfileViewModel
import android.app.Application

object DependencyProvider {
    lateinit var applicationContext: Application

    /* Services (singletons) */
    private val tokenRepository: TokenRepository by lazy { TokenRepository(applicationContext) }
    private val restApi: RestApi by lazy { RestApiFactory(tokenRepository).restApi() }
    private val stringRepository: StringRepository by lazy { StringRepository(applicationContext) }
    private val registerService: RegisterService by lazy { RegisterService(restApi, tokenRepository) }
    private val loginService: LoginService by lazy { LoginService(restApi, tokenRepository) }

    /* ViewModels */
    fun loginViewModel() = LoginViewModel(loginService, stringRepository)
    fun registerViewModel() = RegisterViewModel(registerService, stringRepository)
    fun profileViewModel() = ProfileViewModel(loginService, restApi)
}
