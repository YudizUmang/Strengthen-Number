package com.example.strengthennumber.view.login

import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.os.CountDownTimer
import android.text.Spannable
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.Observer
import com.chaos.view.PinView
import com.example.strengthennumber.R
import com.example.strengthennumber.repository.state.ApiState
import com.example.strengthennumber.view.helper.Helper
import com.example.strengthennumber.viewmodel.otpviewmodel.OTPViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class OtpVerificationActivity : AppCompatActivity() {

    private lateinit var backBtn : Button
    private lateinit var otpView : PinView
    private lateinit var verifyBtn : Button
    private lateinit var resendOtpBtn : Button
    private lateinit var errorText : TextView
    private lateinit var otpTimer : TextView
    private lateinit var progressBar: ProgressBar
    private lateinit var otpTitle2 : TextView
    private lateinit var main : ConstraintLayout
    private val otpViewModel : OTPViewModel by viewModels()
    private val helper = Helper()
    private val activityScope = CoroutineScope(Dispatchers.Main)

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
        progressBar = findViewById(R.id.progressBar)
        otpTitle2 = findViewById(R.id.otp_title2)
        main = findViewById(R.id.main)

        val number:String = intent.getStringExtra("number").toString()
        val message = "We have sent the verification code to your\n$number mobile number."
        val spanString = SpannableString(message)
        val start = message.indexOf(number)
        val end = start + number.length
        spanString.setSpan(StyleSpan(Typeface.BOLD), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

        otpTitle2.text = spanString
        otpViewModel.startTimer()

        otpViewModel.apiResponse.observe(this, Observer {
                state ->
            Log.d("State", state.toString())
            when(state){
                is ApiState.Loading -> {
                    verifyBtn.text = ""
                    progressBar.visibility = View.VISIBLE
                }

                is ApiState.Error -> {
                    progressBar.visibility = View.GONE
                    verifyBtn.text = getString(R.string.verify)
                    helper.showSnackBar(this, main, R.color.errorColor, state.message!!)
                }

                is ApiState.Success ->{
                    progressBar.visibility = View.GONE
                    verifyBtn.text = getString(R.string.verify)
                    val otpIntent = Intent(
                        this,
                        OtpVerificationActivity::class.java
                    )

                    helper.showSnackBar(this, main, R.color.primaryColorP40, state.data.meta?.message!!)
                    activityScope.launch {
                        delay(1500)
                        //start Signup activity
                    }

                }
            }
        })

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
                otpViewModel.verifyUser(otpView.text.toString(), number)
                errorText.visibility = View.INVISIBLE
            }

        })

        backBtn.setOnClickListener {
            finish()
        }

        resendOtpBtn.setOnClickListener {
            if (helper.isOnline(this)) {
                otpViewModel.startTimer()
                otpViewModel.resendOtp(number)
            }else{
                helper.showSnackBar(this, main, R.color.errorColor, getString(R.string.internet_error))
            }
        }

        verifyBtn.setOnClickListener {
            if (helper.isOnline(this)) {
                otpViewModel.checkValidation(this, otpView.text.toString())
            }else{
                helper.showSnackBar(this, main, R.color.errorColor, getString(R.string.internet_error))
            }
        }
    }

    override fun onResume() {
        super.onResume()
        errorText.visibility = View.INVISIBLE
    }

}