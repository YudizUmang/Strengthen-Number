package com.example.strengthennumber.view.signup

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.ProgressBar
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Observer
import androidx.viewpager2.widget.ViewPager2
import com.example.strengthennumber.R
import com.example.strengthennumber.repository.remote.Data
import com.example.strengthennumber.repository.remote.UserResponse
import com.example.strengthennumber.repository.state.ApiState
import com.example.strengthennumber.view.helper.Helper
import com.example.strengthennumber.view.home.HomeActivity
import com.example.strengthennumber.view.login.OtpVerificationActivity
import com.example.strengthennumber.view.signup.fragments.PassData
import com.example.strengthennumber.view.signup.fragments.SignUpFragment1
import com.example.strengthennumber.view.signup.fragments.SignUpFragment2
import com.example.strengthennumber.view.signup.fragments.SignUpFragment3
import com.example.strengthennumber.viewmodel.signupviewmodel.SignUpViewModel
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.google.gson.JsonObject
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@AndroidEntryPoint
class  SignUpActivity : AppCompatActivity(), PassData {
    private lateinit var viewPager: ViewPager2
    private lateinit var tabLayout: TabLayout
    private lateinit var prevButton: Button
    private lateinit var nextButton: Button
    private lateinit var adapter: SignUpAdapter
    private lateinit var progressBar: ProgressBar
    private val signUpViewModel : SignUpViewModel by viewModels()
    private val helper = Helper()
    private lateinit var main : ConstraintLayout
    private val activityScope = CoroutineScope(Dispatchers.Main)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_sign_up)
        tabLayout=findViewById(R.id.signup_tablayout)
        viewPager=findViewById(R.id.signup_viewpager)
        prevButton = findViewById(R.id.previous_btn)
        nextButton=findViewById(R.id.signup_next_btn)
        progressBar = findViewById(R.id.progressBar)
        main = findViewById<ConstraintLayout>(R.id.main)

        adapter = SignUpAdapter( this, supportFragmentManager, lifecycle)
        viewPager.adapter = adapter

       //viewPager.isUserInputEnabled = false

        TabLayoutMediator(tabLayout, viewPager) { _, _ ->
        }.attach()

        val tabs = tabLayout.getChildAt(0) as ViewGroup

        for (i in 0 until tabs.childCount ) {
            val tab = tabs.getChildAt(i)
            val layoutParams = tab.layoutParams as LinearLayout.LayoutParams
            layoutParams.weight = 0f
            layoutParams.marginEnd = 12
            layoutParams.marginStart = 12
            layoutParams.width = 300
            tab.layoutParams = layoutParams
            tabLayout.requestLayout()
        }

        signUpViewModel.apiResponse.observe(this, Observer {
                state ->
            Log.d("State", state.toString())
            when(state){
                is ApiState.Loading -> {
                    nextButton.text = ""
                    progressBar.visibility = View.VISIBLE
                }

                is ApiState.Error -> {
                    progressBar.visibility = View.GONE
                    nextButton.text = getString(R.string.Next)
                    helper.showSnackBar(this, main, R.color.errorColor, state.message!!)
                }

                is ApiState.Success ->{
                    progressBar.visibility = View.GONE
                    nextButton.text = getString(R.string.Next)
                    val home = Intent(
                        this,
                        HomeActivity::class.java
                    )
                    viewPager.currentItem +=1

                    helper.showSnackBar(this, main, R.color.primaryColorP40, state.data.meta?.message!!)
                    activityScope.launch {
                        delay(500)

                    }

                }
            }
        })

        nextButton.setOnClickListener {
            val fragments: List<Fragment> = supportFragmentManager.fragments
            when(val currentFragment: Fragment = fragments[viewPager.currentItem]){
                is SignUpFragment1 -> currentFragment.onButtonClick()
                is SignUpFragment2 -> currentFragment.onButtonClick()
                is SignUpFragment3 -> currentFragment.onButtonClick()
            }


        }

        prevButton.setOnClickListener {
            viewPager.currentItem -=1
        }

        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                updateButtonVisibility(position)
            }
        })
    }

    private fun updateButtonVisibility(position: Int) {

        when (position) {
            0 -> {
                prevButton.visibility = View.GONE
                nextButton.text = getString(R.string.Next)
            }
            1 -> {
                prevButton.visibility = View.VISIBLE
                nextButton.text = getString(R.string.Next)
            }
            2 -> {
                nextButton.text = getString(R.string.submit)
            }
            else -> {
                prevButton.visibility = View.GONE
                nextButton.text = getString(R.string.Next)
            }
        }
    }

    override fun onDataPass(data: JsonObject) {
        Log.d("data", data.toString())
        data.addProperty("contact_number", intent.getStringExtra("number"))
        data.addProperty("id", intent.getIntExtra("id", 0))
        signUpViewModel.setupUserProfile(data)
    }

}