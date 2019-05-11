package agh.vote7.login.ui.register

import agh.vote7.R
import agh.vote7.login.data.Result
import agh.vote7.login.data.register.RegisterRepository
import agh.vote7.login.ui.login.LoginFormState
import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class RegisterViewModel(private val registerRepository: RegisterRepository) : ViewModel() {

    private val _registerForm = MutableLiveData<RegisterFormState>()
    val registerFormState: LiveData<RegisterFormState> = _registerForm

    private val _registerResult = MutableLiveData<RegisterResult>()
    val registerResult: LiveData<RegisterResult> = _registerResult

    fun register(name: String, surname: String, email: String, password: String) {
        val result = registerRepository.register(name, surname, email, password)

        if (result is Result.Success){
            _registerResult.value = RegisterResult(success = RegisteredUserView("Registered"))
        } else {
            _registerResult.value = RegisterResult(error = R.string.registration_failed)
        }
    }

    fun registrationDataChanged(name: String, surname: String, email: String, password: String) {
        if (!isEmailValid(email)) {
            _registerForm.value = RegisterFormState(passwordError = R.string.invalid_password)}
        else if (!isPasswordValid(password)) {
            _registerForm.value = RegisterFormState(passwordError = R.string.invalid_password)}
        else {
            _registerForm.value = RegisterFormState(isDataValid = true)
        }
    }

    private fun isPasswordValid(password: String): Boolean {
        return password.length > 5;
    }

    private fun isEmailValid(email: String): Boolean {
        return if (email.contains('@')) {
            Patterns.EMAIL_ADDRESS.matcher(email).matches()
        } else {
            email.isNotBlank()
        }
    }
}