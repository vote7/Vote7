package agh.vote7.login.ui.register

import agh.vote7.R
import agh.vote7.login.data.Result
import agh.vote7.login.data.register.RegisterService
import agh.vote7.utils.Event
import agh.vote7.utils.StringRepository
import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import timber.log.Timber

class RegisterViewModel(
    private val registerService: RegisterService,
    private val stringRepository: StringRepository
) : ViewModel() {

    private val _registerForm = MutableLiveData<RegisterFormState>()
    val registerFormState: LiveData<RegisterFormState> = _registerForm

    val navigateToMainView = MutableLiveData<Event<Unit>>()

    val showToast = MutableLiveData<Event<String>>()

    fun register(name: String, surname: String, email: String, password: String) = viewModelScope.launch {
        val result = registerService.register(name, surname, email, password)
        when (result) {
            is Result.Success -> {
                navigateToMainView.value = Event(Unit)
            }
            is Result.Error -> {
                Timber.e(result.exception, "Failed to register")
                showToast.value = Event(stringRepository.getString(R.string.registration_failed))
            }
        }
    }

    fun registrationDataChanged(name: String, surname: String, email: String, password: String) {
        if (!isEmailValid(email)) {
            _registerForm.value = RegisterFormState(emailError = R.string.invalid_email)
        } else if (!isPasswordValid(password)) {
            _registerForm.value = RegisterFormState(passwordError = R.string.invalid_password)
        } else {
            _registerForm.value = RegisterFormState(isDataValid = true)
        }
    }

    private fun isPasswordValid(password: String): Boolean {
        return password.isNotEmpty()
    }

    private fun isEmailValid(email: String): Boolean {
        return email.isNotEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
}