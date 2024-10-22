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
            if(response.isSuccessful){
                Log.d("response_login", response.toString())
                return response
            }else{
                return response.errorBody()?.let { Response.error(response.raw().code(), it) }!!
            }

        } catch (e: Exception) {
            Log.d("exception", e.toString())
            Response.error<UserResponse>(1000, ResponseBody.create(MediaType.parse("text/String"), e.toString()))
        }
    }

    suspend fun verifyUserOtp(data : JsonObject) : Response<UserResponse>{
        val response : Response<UserResponse>
        return try{
            response = userApi.verifyOtp(data)
                if(response.isSuccessful){
                    Log.d("response_verifyotp", response.toString())
                    Log.d("header", response.headers().get("X-Authorization-Token").toString())
                    return response
                }else{
                    return response.errorBody()?.let { Response.error(response.raw().code(), it) }!!
                }
        }catch (e :Exception) {
            Log.d("exception", e.toString())
            Response.error<UserResponse>(1000, ResponseBody.create(MediaType.parse("text/String"), e.toString()))
        }
    }
}