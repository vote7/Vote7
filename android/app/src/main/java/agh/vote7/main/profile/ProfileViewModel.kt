package agh.vote7.main.profile

import agh.vote7.data.RestApi
import agh.vote7.login.data.login.LoginService
import agh.vote7.utils.Event
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import timber.log.Timber

class ProfileViewModel(
    private val loginService: LoginService,
    private val restApi: RestApi
) : ViewModel() {

    val loading = MutableLiveData<Boolean>(true)
    val name = MutableLiveData<String>()
    val email = MutableLiveData<String>()
    val groupNames = MutableLiveData<List<String>>()
    val showSnackbar = MutableLiveData<Event<String>>()
    val navigateToLoginView = MutableLiveData<Event<Unit>>()

    init {
        viewModelScope.launch {
            loadCurrentUser()
        }
    }

    fun onLogOutClicked() = viewModelScope.launch {
        loginService.logout()
        navigateToLoginView.value = Event(Unit)
    }

    private suspend fun loadCurrentUser() {
        try {
            val user = restApi.getCurrentUser().await()
            name.value = "${user.name} ${user.surname}"
            email.value = user.email

            // TODO(pjanczyk): remove runCatching after API starts working
            val groups = runCatching { restApi.getUserGroups(user.id).await() }.getOrElse { emptyList() }
            groupNames.value = groups.map { it.name }

            loading.value = false
        } catch (e: Exception) {
            Timber.e(e, "Failed to load user profile")
            showSnackbar.value = Event("Failed to load user profile")
        }
    }
}
