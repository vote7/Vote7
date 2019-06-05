package agh.vote7.login.ui.login

import agh.vote7.R
import agh.vote7.login.data.Result
import agh.vote7.login.data.login.LoginService
import agh.vote7.utils.Event
import agh.vote7.utils.StringRepository
import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import timber.log.Timber

class LoginViewModel(
    private val loginService: LoginService,
    private val stringRepository: StringRepository
) : ViewModel() {
    private val _loginForm = MutableLiveData<LoginFormState>()
    val loginFormState: LiveData<LoginFormState> = _loginForm

    val loadingVisible = MutableLiveData<Boolean>(true)

    val navigateToRegisterView = MutableLiveData<Event<Unit>>()
    val navigateToMainView = MutableLiveData<Event<Unit>>()

    val showToast = MutableLiveData<Event<String>>()

    init {
        viewModelScope.launch {
            if (loginService.isLoggedIn()) {
                navigateToMainView.value = Event(Unit)
            } else {
                loadingVisible.value = false
            }
        }
    }

    fun login(username: String, password: String) = viewModelScope.launch {
        loadingVisible.value = true
        val result = loginService.login(username, password)
        when (result) {
            is Result.Success -> {
                navigateToMainView.value = Event(Unit)
            }
            is Result.Error -> {
                Timber.e(result.exception, "Failed to login")
                loadingVisible.value = false
                showToast.value = Event(stringRepository.getString(R.string.login_failed))
            }
        }
    }

    fun loginDataChanged(username: String, password: String) {
        if (!isUserNameValid(username)) {
            _loginForm.value = LoginFormState(usernameError = R.string.invalid_username)
        } else if (!isPasswordValid(password)) {
            _loginForm.value = LoginFormState(passwordError = R.string.invalid_password)
        } else {
            _loginForm.value = LoginFormState(isDataValid = true)
        }
    }

    fun onRegisterClicked() {
        navigateToRegisterView.value = Event(Unit)
    }

    // A placeholder username validation check
    private fun isUserNameValid(username: String): Boolean {
        return username.isNotEmpty() && Patterns.EMAIL_ADDRESS.matcher(username).matches()
    }

    // A placeholder password validation check
    private fun isPasswordValid(password: String): Boolean {
        return password.isNotEmpty()
    }
}
