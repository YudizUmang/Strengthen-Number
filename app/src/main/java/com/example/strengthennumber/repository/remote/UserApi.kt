package com.example.strengthennumber.repository.remote

import android.service.autofill.UserData
import com.google.gson.JsonObject
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST

interface UserApi {

    @POST("send-otp")
    suspend fun login(@Body contactNumber: JsonObject) : Response<UserResponse>

    @POST("verify-otp")
    suspend fun verifyOtp(@Body jsonObject: JsonObject) : Response<UserResponse>

    @POST("resend-otp")
    suspend fun resendOtp(@Body contactNumber : JsonObject) : Response<UserResponse>

    @Headers("Accept: application/json")
    @POST("edit-profile")
    suspend fun setEditProfile(@Header("Authorization") token : String, @Body userData: JsonObject) : Response<UserResponse>

    @Headers("Accept: application/json")
    @POST("user-profile")
    suspend fun getProfile(@Header("Authorization") token : String) : Response<UserResponse>
}