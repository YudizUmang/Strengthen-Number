package com.example.strengthennumber.view.login

import android.os.Bundle
import android.os.CountDownTimer
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.chaos.view.PinView
import com.example.strengthennumber.R
import com.example.strengthennumber.viewmodel.otpviewmodel.OTPViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OtpVerificationActivity : AppCompatActivity() {

    private lateinit var backBtn : Button
    private lateinit var otpView : PinView
    private lateinit var verifyBtn : Button
    private lateinit var resendOtpBtn : Button
    private lateinit var errorText : TextView
    private lateinit var otpTimer : TextView
    private val otpViewModel : OTPViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_otp_verification)

        backBtn = findViewById(R.id.back_btn)
        otpView = findViewById(R.id.otp_view)
        verifyBtn = findViewById(R.id.verify_btn)
        resendOtpBtn = findViewById(R.id.resend_otp)
        errorText = findViewById(R.id.error_text)
        otpTimer = findViewById(R.id.otp_sec)

        val number = intent.getStringExtra("number")

        otpViewModel.startTimer()

        //timer observer
        otpViewModel.timer.observe(this, Observer {
            time ->
            otpTimer.text = time
        })

        //resend btn observer
        otpViewModel.resendBtn.observe(this, Observer {
            isEnabled ->
            resendOtpBtn.isEnabled = isEnabled
        })

        //validation observer
        otpViewModel.validation.observe(this, Observer {
            err ->
            if(err != null){
                errorText.visibility = View.VISIBLE
                errorText.text = err
            }else{
                otpViewModel.verifyUser(otpView.text.toString(), number!!)
                errorText.visibility = View.INVISIBLE
            }

        })

        backBtn.setOnClickListener {
            finish()
        }

        resendOtpBtn.setOnClickListener {
            otpViewModel.startTimer()
        }

        verifyBtn.setOnClickListener {
            otpViewModel.checkValidation(this, otpView.text.toString())
        }
    }

    override fun onResume() {
        super.onResume()
        errorText.visibility = View.INVISIBLE
    }

}