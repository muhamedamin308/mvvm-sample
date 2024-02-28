package com.example.firstmvvm.ui.auth

import android.content.Intent
import android.view.View
import androidx.lifecycle.ViewModel
import com.example.firstmvvm.data.repo.UserRepository
import com.example.firstmvvm.util.APIException
import com.example.firstmvvm.util.Coroutines
import com.example.firstmvvm.util.NoInternetException

class AuthViewModel(
    private val repository: UserRepository
) : ViewModel() {
    var username: String? = null
    var password: String? = null
    var passwordConfirm: String? = null
    var firstName: String? = null
    var lastName: String? = null
    var age: String? = null
    var email: String? = null
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
            } catch (e: APIException) {
                authListener?.onFailed(e.message!!)
            } catch (e: NoInternetException) {
                authListener?.onFailed(e.message!!)
            }
        }
    }

    fun onSignUpButtonClick(view: View) {
        authListener?.onStarted()
        if (firstName.isNullOrEmpty() || lastName.isNullOrEmpty() ||
            email.isNullOrEmpty()
        ) {
            authListener?.onFailed("Name and Email are Required")
            return
        }
        if (username.isNullOrEmpty()) {
            authListener?.onFailed("Username is Required")
            return
        }
        if (password != passwordConfirm) {
            authListener?.onFailed("Password did not match")
            return
        }
        Coroutines.main {
            try {
                val authResponse = repository.userSignUp(
                    firstName!!,
                    lastName!!,
                    age!!,
                    email!!,
                    username!!,
                    password!!
                )
                authResponse?.let {
                    authListener?.onSuccess(it)
                    repository.saveUser(it)
                    return@main
                }
            } catch (e: APIException) {
                authListener?.onFailed(e.message!!)
            } catch (e: NoInternetException) {
                authListener?.onFailed(e.message!!)
            }
        }
    }

    fun onSignUp(view: View) {
        Intent(view.context, SignUpActivity::class.java).also {
            view.context.startActivity(it)
        }
    }

    fun onLogin(view: View) {
        Intent(view.context, LoginActivity::class.java).also {
            view.context.startActivity(it)
        }
    }
}