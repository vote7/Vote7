package agh.vote7.main.profile

import agh.vote7.data.RestApi
import agh.vote7.data.RestApiProvider
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class ProfileViewModel : ViewModel() {
    private val restApi: RestApi = RestApiProvider.restApi

    val name = MutableLiveData<String>()
    val email = MutableLiveData<String>()
    val snackbar = MutableLiveData<String>()

    init {
        viewModelScope.launch {
            loadCurrentUser()
        }
    }

    fun onLogOutClicked() {
        // TODO(pjanczyk): implement logout
    }

    private suspend fun loadCurrentUser() {
        try {
            val user = restApi.getUser().await()
            name.value = "${user.name} ${user.surname}"
            email.value = user.email
        } catch (e: Exception) {
            snackbar.value = "Failed to load user profile"
        }
    }
}
