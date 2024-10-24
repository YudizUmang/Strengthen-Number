package com.example.strengthennumber.viewmodel.otpviewmodel

import android.content.Context
import android.content.SharedPreferences
import android.os.CountDownTimer
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.strengthennumber.R
import com.example.strengthennumber.repository.LoginRepo
import com.example.strengthennumber.repository.remote.UserResponse
import com.example.strengthennumber.repository.state.ApiState
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OTPViewModel @Inject constructor(private val repo : LoginRepo, private val sharedpref : SharedPreferences): ViewModel() {

    private val sharedPrefEditor = sharedpref.edit()

    //state liveData
    private val _apiResponse = MutableLiveData<ApiState<UserResponse>>()
    val apiResponse: LiveData<ApiState<UserResponse>> = _apiResponse


    //local validation liveData
    private val _validation = MutableLiveData<String?>()
    val validation: MutableLiveData<String?> get() = _validation

    //Resend live data
    private val _resendBtn = MutableLiveData<Boolean>()
    val resendBtn: MutableLiveData<Boolean> get() = _resendBtn

    //timer liver data
    private val _timer = MutableLiveData<String>()
    val timer: MutableLiveData<String> get() = _timer



    fun startTimer(){
        _resendBtn.value = false
        val cTimer = object : CountDownTimer(30000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                _timer.value = "${(millisUntilFinished / 1000)} sec"
            }
            override fun onFinish() {
                _resendBtn.value = true
            }
        }
        cTimer.start()
    }

    fun checkValidation(context: Context, otp : String){
        _validation.value = if(otp.length < 4)  context.getString(R.string.errorTxt) else null
    }

    fun verifyUser(otp : String, number : String){
        _apiResponse.postValue(ApiState.Loading)
        val jsonObject = JsonObject()
        jsonObject.addProperty("contact_number", number)
        jsonObject.addProperty("otp", otp.toInt())

        viewModelScope.launch(Dispatchers.IO) {
            val result = repo.verifyUserOtp(jsonObject)

            if (result.isSuccessful){
                _apiResponse.postValue(ApiState.Success(result.body()!!))
                sharedPrefEditor.putString("X-Authorization-Token", result.headers().get("X-Authorization-Token"))
                sharedPrefEditor.apply()
                Log.d("userData", result.body().toString())
            }else{
                val gson = Gson()
                val type = object : TypeToken<UserResponse>() {}.type
                val errorResponse: UserResponse? = gson.fromJson(result.errorBody()!!.charStream(), type)
                Log.d("Failure", errorResponse.toString())
                _apiResponse.postValue(
                    ApiState.Error(
                        errorResponse?.meta?.message))
            }
        }
    }

    fun resendOtp(number: String){
        _apiResponse.postValue(ApiState.Loading)
        val jsonObject = JsonObject()
        jsonObject.addProperty("contact_number", number)
        viewModelScope.launch(Dispatchers.IO) {
            val result = repo.resendOtpUser(jsonObject)
            if (result.isSuccessful){
                _apiResponse.postValue(ApiState.Success(result.body()!!))

                Log.d("userData", result.body().toString())
            }else{
                val gson = Gson()
                val type = object : TypeToken<UserResponse>() {}.type
                val errorResponse: UserResponse? = gson.fromJson(result.errorBody()!!.charStream(), type)
                Log.d("Failure", errorResponse.toString())
                _apiResponse.postValue(ApiState.Error(errorResponse?.meta?.message))
            }
        }
    }
}