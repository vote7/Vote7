package agh.vote7.main.profile

import agh.vote7.data.RestApi
import agh.vote7.data.RestApiProvider
import agh.vote7.login.data.login.LoginDataSource
import agh.vote7.utils.Event
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import timber.log.Timber

class ProfileViewModel : ViewModel() {
    private val restApi: RestApi = RestApiProvider.restApi

    val loading = MutableLiveData<Boolean>(true)
    val name = MutableLiveData<String>()
    val email = MutableLiveData<String>()
    val groupNames = MutableLiveData<List<String>>()
    val snackbar = MutableLiveData<String>()
    val navigateToLoginView = MutableLiveData<Event<Unit>>()

    init {
        viewModelScope.launch {
            loadCurrentUser()
        }
    }

    fun onLogOutClicked() {
        LoginDataSource.logout()
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
            snackbar.value = "Failed to load user profile"
        }
    }
}
