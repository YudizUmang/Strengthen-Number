package com.example.strengthennumber.view.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.Observer
import com.example.strengthennumber.R
import com.example.strengthennumber.repository.state.ApiState
import com.example.strengthennumber.view.helper.Helper
import com.example.strengthennumber.viewmodel.loginviewmodel.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    private val loginViewModel : LoginViewModel by viewModels()
    private lateinit var loginEditText: EditText
    private lateinit var loginContinueBtn : Button
    private lateinit var loginErrorText : TextView
    private lateinit var progressBar: ProgressBar
    private lateinit var main : ConstraintLayout
    private val helper = Helper()
    private val activityScope = CoroutineScope(Dispatchers.Main)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)
        loginEditText = findViewById(R.id.edit_text_login)
        loginContinueBtn = findViewById(R.id.continue_btn)
        loginErrorText = findViewById(R.id.error_text)
        progressBar = findViewById(R.id.progressBar)
        main = findViewById(R.id.main)

        //Ui Handling
        loginViewModel.apiResponse.observe(this, Observer {
            state ->
            Log.d("State", state.toString())
            when(state){
                is ApiState.Loading -> {
                    loginContinueBtn.text = ""
                    progressBar.visibility = View.VISIBLE
                }

                is ApiState.Error -> {
                    progressBar.visibility = View.GONE
                    loginContinueBtn.text = getString(R.string.continue_text)
                    helper.showSnackBar(this, main, R.color.primaryColorP40, state.message!!)
                    state.message?.let { Log.d("error", it) }
                }

                is ApiState.Success ->{
                    progressBar.visibility = View.GONE
                    loginContinueBtn.text = getString(R.string.continue_text)
                    val otpIntent = Intent(
                        this,
                        OtpVerificationActivity::class.java
                    )

                    helper.showSnackBar(this, main, R.color.primaryColorP40, state.data.meta?.message!!)
                    activityScope.launch {
                        delay(500)
                        otpIntent.putExtra("number", loginEditText.text.toString())
                        startActivity(otpIntent)                   }

                }
            }
        })

        //login validation observer
        loginViewModel.validation.observe(this, Observer { message ->
            if(message != null) {
                loginErrorText.visibility = View.VISIBLE
                loginErrorText.text = message
            }else{
                loginViewModel.sendOTPtoUser(loginEditText.text.toString())
            }
        })

        loginContinueBtn.setOnClickListener {
            if(helper.isOnline(this))
                loginViewModel.checkLoginValidation(this, loginEditText.text.isEmpty(), loginEditText.text.length)
            else
                helper.showSnackBar(this, main, R.color.errorColor, getString(R.string.internet_error))
     }
    }
}