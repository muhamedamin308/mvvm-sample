package com.example.firstmvvm.data.network

import com.example.firstmvvm.util.APIException
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Response

abstract class SafeApiRequest {
    suspend fun<T: Any> apiRequest(call: suspend () -> Response<T>): T? {
        val response = call.invoke()
        if (response.isSuccessful) {
            return response.body()!!
        } else {
            val error = response.body()?.toString()
            val messageBuilder = StringBuilder()
            error?.let {
                try {
                    messageBuilder.append(JSONObject(it).getString("message"))
                } catch (e: JSONException) {
                    messageBuilder.append("\n")
                }
            }
            messageBuilder.append("Error Code: ${response.code()}")
            throw APIException(messageBuilder.toString())
        }
    }
}