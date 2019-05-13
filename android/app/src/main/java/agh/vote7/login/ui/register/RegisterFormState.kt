package agh.vote7.login.ui.register

data class RegisterFormState (
    val nameError: Int? = null,
    val surnameError: Int? = null,
    val emailError: Int? = null,
    val passwordError: Int? = null,
    val isDataValid: Boolean = false
)