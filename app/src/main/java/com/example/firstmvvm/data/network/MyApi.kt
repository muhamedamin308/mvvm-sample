package com.example.firstmvvm.data.network

import com.example.firstmvvm.data.db.entities.User
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST


interface MyApi {
    @FormUrlEncoded
    @POST("login")
    suspend fun userLogin(
        @Field("username") username: String,
        @Field("password") password: String
    ): Response<User>

    @FormUrlEncoded
    @POST("add")
    suspend fun userSignUp(
        @Field("firstName") firstName: String,
        @Field("lastName") lastName: String,
        @Field("age") age: String,
        @Field("email") email: String,
        @Field("username") username: String,
        @Field("password") password: String
    ): Response<User>

    companion object {
        operator fun invoke(
            networkConnectionInterceptor: NetworkConnectionInterceptor
        ): MyApi {
            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(networkConnectionInterceptor)
                .build()

            return Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl("https://dummyjson.com/add")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(MyApi::class.java)
        }
    }
}
