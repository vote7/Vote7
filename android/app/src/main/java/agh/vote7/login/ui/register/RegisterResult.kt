package agh.vote7.login.ui.register

import agh.vote7.login.data.Result

data class RegisterResult (
    val success: RegisteredUserView? = null,
    val error: Int? = null
)