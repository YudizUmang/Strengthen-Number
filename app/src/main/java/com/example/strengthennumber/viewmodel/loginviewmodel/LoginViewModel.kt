package com.example.strengthennumber.viewmodel.loginviewmodel

import android.content.Context
import android.service.autofill.UserData
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.strengthennumber.R
import com.example.strengthennumber.repository.LoginRepo
import com.example.strengthennumber.repository.remote.UserResponse
import com.example.strengthennumber.repository.state.ApiState
import com.google.gson.JsonObject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val repo: LoginRepo) : ViewModel() {

    //state liveData
    private val _apiResponse = MutableLiveData<ApiState<UserResponse>>()
    val apiResponse: LiveData<ApiState<UserResponse>> = _apiResponse


    //local validation liveData
    private val _validation = MutableLiveData<String?>()
    val validation: MutableLiveData<String?> get() = _validation

    fun checkLoginValidation(context: Context, isEmpty: Boolean, length: Int) {
        _validation.value = if (isEmpty || length != 10) {
            context.getString(R.string.login_error_text)
        } else {
            null
        }
    }

    fun sendOTPtoUser(contactNumber: String) {
        _apiResponse.value = ApiState.Loading
        viewModelScope.launch(Dispatchers.IO) {

            val jsonObject = JsonObject()
            jsonObject.addProperty("contact_number", contactNumber)

            val result = repo.sendOtp(jsonObject)
            if (result.isSuccessful){
               // _userLoginData.postValue(result.body()?.meta?.message)
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