package agh.vote7.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

fun <T> viewModelProviderFactory(provider: () -> T): ViewModelProvider.Factory =
    object : ViewModelProvider.Factory {
        override fun <U : ViewModel> create(modelClass: Class<U>): U {
            val viewModel = provider()
            return modelClass.cast(viewModel)!!
        }
    }
