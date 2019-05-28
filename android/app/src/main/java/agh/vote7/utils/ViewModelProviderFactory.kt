package agh.vote7.utils

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.get

fun <T> viewModelProviderFactory(provider: () -> T): ViewModelProvider.Factory =
    object : ViewModelProvider.Factory {
        override fun <U : ViewModel> create(modelClass: Class<U>): U {
            val viewModel = provider()
            return modelClass.cast(viewModel)!!
        }
    }

inline fun <reified VM : ViewModel> Fragment.getViewModel(noinline provider: () -> VM): VM =
    ViewModelProviders.of(this, viewModelProviderFactory(provider)).get()

inline fun <reified VM : ViewModel> FragmentActivity.getViewModel(noinline provider: () -> VM): VM =
    ViewModelProviders.of(this, viewModelProviderFactory(provider)).get()