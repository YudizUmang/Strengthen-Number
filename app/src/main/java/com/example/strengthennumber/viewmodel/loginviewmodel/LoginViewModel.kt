package com.example.strengthennumber.viewmodel.loginviewmodel

import android.service.autofill.UserData
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.strengthennumber.repository.LoginRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(repo : LoginRepo) : ViewModel(){
    private val _userLoginData = MutableLiveData<UserData>()
    val userLoginData: LiveData<UserData> get() = _userLoginData

    fun sendOTPtoUser() : UserData{
        viewModelScope.launch{

        }

    }
}