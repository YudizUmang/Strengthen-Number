package com.example.strengthennumber

import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowInsets
import android.view.WindowManager
import androidx.annotation.RequiresApi
import com.example.strengthennumber.view.home.HomeActivity
import com.example.strengthennumber.view.onboarding.OnBoardingActivity
import com.example.strengthennumber.view.signup.SignUpActivity

import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {


    @Inject lateinit var sharedPref : SharedPreferences
    private val activityScope = CoroutineScope(Dispatchers.Main)

    private lateinit var sharedPrefEditor : SharedPreferences.Editor


    @RequiresApi(Build.VERSION_CODES.R)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        sharedPrefEditor = sharedPref.edit()

        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN)
        window.insetsController?.hide(WindowInsets.Type.statusBars())

        activityScope.launch {
            delay(3000)
            checkUser()
            //startActivity(Intent(this@MainActivity, OnBoardingActivity::class.java))
        }

    }

    override fun onPause() {
        activityScope.cancel()
        super.onPause()
    }


    private fun checkUser(){
        if(sharedPref.contains("registerUser")){
            startActivity(Intent(this, HomeActivity::class.java))
        }else if(sharedPref.contains("isUser")){
            val intent = Intent(this, SignUpActivity::class.java)
            intent.putExtra("fromMain", true)
            startActivity(intent)
        }
        else {
            sharedPrefEditor.remove("id")
            sharedPrefEditor.remove("isUser")
            sharedPrefEditor.remove("X-Authorization-Token")
            sharedPrefEditor.apply()
            startActivity(Intent(this, OnBoardingActivity::class.java))
        }
    }
}