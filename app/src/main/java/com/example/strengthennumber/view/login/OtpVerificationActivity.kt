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
import androidx.appcompat.app.AppCompatActivity
import com.chaos.view.PinView
import com.example.strengthennumber.R


class OtpVerificationActivity : AppCompatActivity() {
    private lateinit var backBtn : Button
    private lateinit var otpView : PinView
    private lateinit var verifyBtn : Button
    private lateinit var resendOtpBtn : Button
    private lateinit var errorText : TextView
    private lateinit var otpTimer : TextView
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
        startTimer()

        backBtn.setOnClickListener {
            finish()
        }

        resendOtpBtn.setOnClickListener {
            startTimer()
        }

        verifyBtn.setOnClickListener {
            if(otpView.text?.length!! < 4){
                errorText.visibility = View.VISIBLE
                errorText.text = "Please enter 4 digit OTP"
            }
        }


    }

    private fun startTimer(){
        resendOtpBtn.isEnabled = false
        val cTimer = object : CountDownTimer(30000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                otpTimer.text = "${(millisUntilFinished / 1000)} sec"
            }
            override fun onFinish() {
                resendOtpBtn.isEnabled = true
            }
        }
        cTimer.start()
    }
}