package com.example.strengthennumber.repository.remote

import android.service.autofill.UserData
import com.google.gson.JsonObject
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface UserApi {

    @POST("send-otp")
    suspend fun login(@Body contactNumber: JsonObject) : Response<UserResponse>

    @POST("verify-otp")
    suspend fun verifyOtp(@Body jsonObject: JsonObject) : Response<UserResponse>

    @POST("resend-otp")
    suspend fun resendOtp(@Body contactNumber : JsonObject) : Response<UserResponse>

    @POST("edit-profile")
    suspend fun setEditProfile(@Body userData: UserData) : Response<UserResponse>
}