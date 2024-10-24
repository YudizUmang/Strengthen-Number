package com.example.strengthennumber.viewmodel.signupviewmodel

import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.strengthennumber.repository.SignUpRepo
import com.example.strengthennumber.repository.remote.Data
import com.example.strengthennumber.repository.remote.UserResponse
import com.example.strengthennumber.repository.state.ApiState
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val repo : SignUpRepo,
    private val sharedPref : SharedPreferences
) : ViewModel() {

    //state liveData
    private val _apiResponse = MutableLiveData<ApiState<UserResponse>>()
    val apiResponse: LiveData<ApiState<UserResponse>> = _apiResponse

    fun setupUserProfile(data : JsonObject){
        _apiResponse.value = ApiState.Loading
            val token = sharedPref.getString("X-Authorization-Token", null)
            Log.d("token", token.toString())
//            val jsonObjectToken = JsonObject()
//            jsonObjectToken.addProperty("X-Authorization-Token", token.toString())
        if(token != null) {
            viewModelScope.launch(Dispatchers.IO) {
                val result = repo.setupProfile("Bearer $token", data)
                if (result.isSuccessful) {
                    _apiResponse.postValue(ApiState.Success(result.body()!!))
                    Log.d("userData setupProfile", result.body().toString())
                } else {
                    val gson = Gson()
                    val type = object : TypeToken<UserResponse>() {}.type
                    val errorResponse =
                        gson.fromJson<UserResponse>(result.errorBody()!!.charStream(), type)
                    Log.d("Failure", errorResponse.toString())
                    _apiResponse.postValue(
                        ApiState.Error(errorResponse?.meta?.message)
                    )
                }
            }
        }else{
            Log.d("null token", "Token is null")
        }
    }
}