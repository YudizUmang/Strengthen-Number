package com.example.strengthennumber.view.login

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.Selection
import android.text.TextWatcher
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.strengthennumber.R
import com.example.strengthennumber.viewmodel.loginviewmodel.LoginViewModel


class LoginActivity : AppCompatActivity() {

    val loginViewModel : LoginViewModel by viewModels()
    private lateinit var loginEditText: EditText
    private lateinit var loginContinueBtn : Button
    private lateinit var loginErrorText : TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)
        loginEditText = findViewById(R.id.edit_text_login)
        loginContinueBtn = findViewById(R.id.continue_btn)
        loginErrorText = findViewById(R.id.error_text)

        loginEditText.setOnFocusChangeListener { _, b ->
            if(b){
                loginEditText.setText("+91")
            }
        }

        loginContinueBtn.setOnClickListener {
            if(loginEditText.text.isEmpty() || loginEditText.text.length != 13){
                loginEditText.error = getString(R.string.login_error_text)
                loginErrorText.visibility = View.VISIBLE
                loginErrorText.text = getString(R.string.login_error_text)
            }else{
                startActivity(Intent(this, OtpVerificationActivity::class.java))
            }
        }

    }
}