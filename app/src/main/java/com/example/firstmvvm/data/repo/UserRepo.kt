package com.example.firstmvvm.data.repo

import com.example.firstmvvm.data.db.entities.User
import com.example.firstmvvm.data.network.MyApi
import retrofit2.Response

class UserRepo {
    suspend fun userLogin(email: String, password: String): Response<User> =
        MyApi().userLogin(email, password)
}