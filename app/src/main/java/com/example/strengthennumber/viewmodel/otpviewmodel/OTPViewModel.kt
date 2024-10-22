package com.example.strengthennumber.viewmodel.otpviewmodel

import android.content.Context
import android.content.SharedPreferences
import android.os.CountDownTimer
import android.util.Log
import android.widget.TextView
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.strengthennumber.R
import com.example.strengthennumber.repository.LoginRepo
import com.example.strengthennumber.repository.local.SharedPref
import com.example.strengthennumber.repository.remote.UserResponse
import com.example.strengthennumber.repository.state.ApiState
import com.google.gson.JsonObject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OTPViewModel @Inject constructor(private val repo : LoginRepo, private val sharedPref : SharedPreferences): ViewModel() {
    private val sharedPrefEditor = sharedPref.edit()
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
        val jsonObject = JsonObject()
        jsonObject.addProperty("contact_number", number)
        jsonObject.addProperty("otp", otp.toInt())
        viewModelScope.launch(Dispatchers.IO) {
            val result = repo.verifyUserOtp(jsonObject)
            if (result.isSuccessful){
                //_userLoginData.postValue(result.body()?.meta?.message)
                sharedPrefEditor.putString("X-Authorization-Token", result.headers().get("X-Authorization-Token"))
                _apiResponse.postValue(ApiState.Success(result.body()!!))
                Log.d("userData", result.body().toString())
            }else{
                Log.d("failure", result.errorBody().toString())
                //_userLoginData.postValue(result.message)
                _apiResponse.postValue(ApiState.Error(result.errorBody().toString()))
            }
        }
    }
}