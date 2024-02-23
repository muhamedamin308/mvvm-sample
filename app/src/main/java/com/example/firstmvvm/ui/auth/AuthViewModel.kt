package com.example.firstmvvm.ui.auth

import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModel
import com.example.firstmvvm.data.repo.UserRepo
import com.example.firstmvvm.util.Coroutines

class AuthViewModel : ViewModel() {
    var username: String? = null
    var password: String? = null
    var authListener: AuthListener? = null
    fun onLoginButtonClick(view: View) {
        authListener?.onStarted()
        if (username.isNullOrEmpty() || password.isNullOrEmpty()) {
            authListener?.onFailed("Please fill the empty fields")
            return
        }
        Coroutines.main {
            val response = UserRepo().userLogin(username!!, password!!)
            if (response.isSuccessful) {
                authListener?.onSuccess(response.body()!!)
            } else {
                authListener?.onFailed("Error Message: ${response.body()?.message}")
            }
        }
    }
}