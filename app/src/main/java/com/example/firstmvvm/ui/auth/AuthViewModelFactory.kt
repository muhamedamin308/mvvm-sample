package com.example.firstmvvm.ui.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.firstmvvm.data.repo.UserRepo

@Suppress("UNCHECKED_CAST")
class AuthViewModelFactory(
    private val repository: UserRepo
): ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        AuthViewModel(repository) as T
}