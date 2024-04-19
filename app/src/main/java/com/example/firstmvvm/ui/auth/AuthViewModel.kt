package com.example.firstmvvm.ui.auth

import androidx.lifecycle.ViewModel
import com.example.firstmvvm.data.db.entities.User
import com.example.firstmvvm.data.repo.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AuthViewModel(
    private val repository: UserRepository
) : ViewModel() {

    fun getLoggedInUser() = repository.getUser()

    suspend fun userLogin(
        email: String, password: String
    ) = withContext(Dispatchers.IO) {
        repository.userLogin(email, password)
    }

    suspend fun userSingUp(
        firstName: String,
        lastName: String,
        age: String,
        email: String,
        username: String,
        password: String
    ) = withContext(Dispatchers.IO) {
        repository.userSignUp(firstName, lastName, age, email, username, password)
    }

    suspend fun saveLoggedUser(user: User) = repository.saveUser(user)
}