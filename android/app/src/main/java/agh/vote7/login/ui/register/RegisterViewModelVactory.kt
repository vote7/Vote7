package agh.vote7.login.ui.register

import agh.vote7.login.data.login.LoginDataSource
import agh.vote7.login.data.login.LoginRepository
import agh.vote7.login.data.register.RegisterDataSource
import agh.vote7.login.data.register.RegisterRepository
import agh.vote7.login.ui.login.LoginViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class RegisterViewModelVactory : ViewModelProvider.Factory{
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RegisterViewModel::class.java)) {
            return RegisterViewModel(
                registerRepository = RegisterRepository(
                    dataSource = RegisterDataSource()
                )
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}