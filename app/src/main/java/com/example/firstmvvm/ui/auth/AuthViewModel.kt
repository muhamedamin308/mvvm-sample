package com.example.firstmvvm.ui.auth

import android.view.View
import androidx.lifecycle.ViewModel
import com.example.firstmvvm.data.repo.UserRepo
import com.example.firstmvvm.util.APIException
import com.example.firstmvvm.util.Coroutines
import com.example.firstmvvm.util.NoInternetException

class AuthViewModel(
    private val repository: UserRepo
): ViewModel() {
    var username: String? = null
    var password: String? = null
    var authListener: AuthListener? = null

    fun getLoggedInUser() = repository.getUser()
    fun onLoginButtonClick(view: View) {
        authListener?.onStarted()
        if (username.isNullOrEmpty() || password.isNullOrEmpty()) {
            authListener?.onFailed("Please fill the empty fields")
            return
        }
        Coroutines.main {
            try {
                val authResponse = repository.userLogin(username!!, password!!)
                authResponse?.let {
                    authListener?.onSuccess(it)
                    repository.saveUser(it)
                    return@main
                }
//                authListener?.onFailed(authResponse?.message!!)
            }catch (e: APIException) {
                authListener?.onFailed(e.message!!)
            } catch (e: NoInternetException) {
                authListener?.onFailed(e.message!!)
            }
        }
    }
}