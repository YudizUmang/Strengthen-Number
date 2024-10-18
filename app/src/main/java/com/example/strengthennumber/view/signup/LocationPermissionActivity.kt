package com.example.strengthennumber.view.signup

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.strengthennumber.R

class LocationPermissionActivity : AppCompatActivity() {
    private lateinit var allowBtn : Button
    private lateinit var doNotAllowBtn : Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_location_permission)
        allowBtn = findViewById(R.id.allow_btn)
        doNotAllowBtn = findViewById(R.id.dont_allow_btn)

        allowBtn.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
        }

        doNotAllowBtn.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
        }


    }
}