package com.example.firstmvvm.ui.auth

import com.example.firstmvvm.data.db.entities.User

interface AuthListener {
    fun onStarted()
    fun onSuccess(user: User)
    fun onFailed(message: String)
}