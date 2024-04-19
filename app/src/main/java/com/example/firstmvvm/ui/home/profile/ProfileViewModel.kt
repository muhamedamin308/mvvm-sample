package com.example.firstmvvm.ui.home.profile

import androidx.lifecycle.ViewModel
import com.example.firstmvvm.data.repo.UserRepository

class ProfileViewModel(
    private val repository: UserRepository
) : ViewModel() {
    val user = repository.getUser()

}