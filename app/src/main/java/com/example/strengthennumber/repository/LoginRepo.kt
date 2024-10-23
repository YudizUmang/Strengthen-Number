package com.example.strengthennumber.repository

import android.util.Log
import com.example.strengthennumber.repository.remote.UserApi
import com.example.strengthennumber.repository.remote.UserResponse
import com.google.gson.JsonObject
import okhttp3.MediaType
import okhttp3.ResponseBody
import retrofit2.Response
import javax.inject.Inject

class LoginRepo @Inject constructor(private val userApi : UserApi) {

    suspend fun sendOtp(contactNumber : JsonObject) : Response<UserResponse>{
        val response : Response<UserResponse>
         return try {
            response = userApi.login(contactNumber)
            response
        } catch (e: Exception) {
            Log.d("exception", e.toString())
             Response.error<UserResponse>(1000, ResponseBody.create(MediaType.parse("text/String"), e.toString()))
        }
    }

    suspend fun verifyUserOtp(data : JsonObject) : Response<UserResponse>{
        val response : Response<UserResponse>
        return try{
            response = userApi.verifyOtp(data)
            response
        }catch (e :Exception) {
            Log.d("exception", e.toString())
            Response.error<UserResponse>(1000, ResponseBody.create(MediaType.parse("text/String"), e.toString()))
        }
    }

    suspend fun resendOtpUser(number : JsonObject) : Response<UserResponse>{
        val response : Response<UserResponse>
        return try{
            response = userApi.resendOtp(number)
            response

        }catch (e :Exception) {
            Log.d("exception", e.toString())
            Response.error<UserResponse>(1000, ResponseBody.create(MediaType.parse("text/String"), e.toString()))
        }
    }
}