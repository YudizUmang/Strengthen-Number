package com.example.strengthennumber.repository.remote

import android.service.autofill.UserData
import com.google.gson.JsonObject
import retrofit2.http.Body
import retrofit2.http.POST

interface UserApi {

    @POST("/send-otp")
    suspend fun login(contactNumber: String) : UserData

    @POST("/verify-otp")
    suspend fun verifyOtp(@Body jsonObject: JsonObject) : UserData

    @POST("/resend-otp")
    suspend fun resendOtp(contactNumber : String) : UserData

    @POST("/edit-profile")
    suspend fun setEditProfile(@Body userData: UserData) : UserData
}