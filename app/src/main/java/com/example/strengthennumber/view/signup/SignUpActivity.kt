package com.example.strengthennumber.view.signup

import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.viewpager2.widget.ViewPager2
import com.example.strengthennumber.R
import com.example.strengthennumber.view.onboarding.ViewPagerAdapter
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class SignUpActivity : AppCompatActivity() {
    private lateinit var viewPager: ViewPager2
    private lateinit var tabLayout: TabLayout
    private lateinit var prevButton: Button
    private lateinit var nextButton: Button
    private lateinit var adapter: SignUpAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_sign_up)
        tabLayout=findViewById(R.id.signup_tablayout)
        viewPager=findViewById(R.id.signup_viewpager)
        prevButton = findViewById(R.id.previous_btn)
        nextButton=findViewById(R.id.signup_next_btn)

        adapter = SignUpAdapter(this, supportFragmentManager, lifecycle)
        viewPager.adapter = adapter

        TabLayoutMediator(tabLayout, viewPager) { _, _ ->
        }.attach()

        nextButton.setOnClickListener {
            viewPager.currentItem +=1
        }

        prevButton.setOnClickListener {
            viewPager.currentItem -=1
        }
    }
}