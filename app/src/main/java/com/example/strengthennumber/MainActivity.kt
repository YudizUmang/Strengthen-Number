package com.example.strengthennumber

import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowInsets
import android.view.WindowManager
import androidx.annotation.RequiresApi
import com.example.strengthennumber.view.onboarding.OnBoardingActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject lateinit var sharedPref : SharedPreferences
    private val activityScope = CoroutineScope(Dispatchers.Main)

    @RequiresApi(Build.VERSION_CODES.R)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN)
        window.insetsController?.hide(WindowInsets.Type.statusBars())



        activityScope.launch {
            delay(3000)
            startActivity(Intent(this@MainActivity, OnBoardingActivity::class.java))
        }
    }

    override fun onPause() {
        activityScope.cancel()
        super.onPause()
    }

//    private fun checkUser(){
//        if(sharedPref.contains("X-Authorization-Token")){
//            startActivity(Intent(this, HomeActivity::class.java))
//        }else{
//            startActivity(Intent(this, OnBoardingActivity::class.java))
//        }
//    }
}