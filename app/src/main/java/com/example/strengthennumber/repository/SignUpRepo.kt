package com.example.strengthennumber.repository

import android.util.Log
import com.example.strengthennumber.repository.remote.Data
import com.example.strengthennumber.repository.remote.UserApi
import com.example.strengthennumber.repository.remote.UserResponse
import com.google.gson.JsonObject
import okhttp3.MediaType
import okhttp3.ResponseBody
import retrofit2.Response
import javax.inject.Inject

class SignUpRepo @Inject constructor(private val userApi : UserApi) {
    suspend fun setupProfile(token : String, data : JsonObject) : Response<UserResponse>{
        val response : Response<UserResponse>

        return try {
            response = userApi.setEditProfile(token, data)
            Log.d("result", response.toString())
            response
        } catch (e: Exception) {
            Log.d("exception", e.toString())
            Response.error<UserResponse>(1000, ResponseBody.create(MediaType.parse("text/String"), e.toString()))
        }
    }

    suspend fun getProfile(token : String):Response<UserResponse>{
        val response : Response<UserResponse>

        return try {
            response = userApi.getProfile(token)
            Log.d("result", response.toString())
            response
        } catch (e: Exception) {
            Log.d("exception", e.toString())
            Response.error<UserResponse>(1000, ResponseBody.create(MediaType.parse("text/String"), e.toString()))
        }
    }
}