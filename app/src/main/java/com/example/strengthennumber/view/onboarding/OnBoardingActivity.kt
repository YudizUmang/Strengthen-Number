package com.example.strengthennumber.view.onboarding

import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.example.strengthennumber.R
import com.example.strengthennumber.view.login.LoginActivity
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class OnBoardingActivity : AppCompatActivity(), OnBoardingButtons {
    private lateinit var viewPager : ViewPager2
    private lateinit var adapter: ViewPagerAdapter
    private lateinit var tabLayout: TabLayout
    private lateinit var titleTextView : TextView
    private lateinit var descTextView : TextView
    private lateinit var skipBtn : Button
    private lateinit var nextBtn : Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_onboarding)

        val onBoardList = listOf<Fragment>(
            OnBoarding(
                getDrawableFromImg(R.drawable.on_board1),
                getString(R.string.onboarding_title1),
                getString(R.string.onboarding_desc1),
                canSkip = true,
                isLast = false,
                listener = this
            ),
            OnBoarding(
                getDrawableFromImg(R.drawable.on_board2),
                getString(R.string.onboarding_title2),
                getString(R.string.onboarding_desc2),
                canSkip = true,
                isLast = false,
                listener = this
            ),
            OnBoarding(
                getDrawableFromImg(R.drawable.on_board3),
                getString(R.string.onboarding_title3),
                getString(R.string.onboarding_desc3),
                canSkip = false,
                isLast = true,
                listener = this
            )
        )
        tabLayout = findViewById(R.id.into_tab_layout)
        viewPager = findViewById(R.id.onboarding_viewPager)
        titleTextView = findViewById(R.id.onboard_title)
        descTextView = findViewById(R.id.onboard_desc)
        skipBtn = findViewById(R.id.skip_btn)
        nextBtn = findViewById(R.id.next_btn)
        adapter = ViewPagerAdapter(onBoardList, supportFragmentManager, lifecycle)
        viewPager.adapter = adapter

        TabLayoutMediator(tabLayout, viewPager) { _, _ ->
        }.attach()
        updateUIForPage(viewPager.currentItem)
        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                    Log.d("onPageSelected Position", position.toString())
                    updateUIForPage(position)
                }
        })
    }

    private fun updateUIForPage(position: Int) {
        Log.d("updateUI Position", position.toString())
        val currentFragment = adapter.createFragment(position) as OnBoarding
        setContent(currentFragment.title, currentFragment.desc, currentFragment.canSkip, currentFragment.isLast)
    }

    private fun getDrawableFromImg(id: Int): Drawable? {
        return AppCompatResources.getDrawable(this, id) ?: AppCompatResources.getDrawable(
            this,
            R.drawable.logo
        )
    }

//    override fun skipBtnClick() {
//        viewPager.currentItem = (viewPager.adapter?.itemCount ?: 0) - 1
//    }
//
//    override fun nextBtnClick(text: String) {
//        val nextItem = viewPager.currentItem + 1
//        if (nextItem < (viewPager.adapter?.itemCount ?: 0)) {
//            viewPager.currentItem = nextItem
//        } else {
//            startActivity(Intent(this, LoginActivity::class.java) )
//            finish()
//        }
//    }


    override fun setContent(title: String, desc: String, canSkip: Boolean, isLast: Boolean) {
        Log.d("setContent isLast", isLast.toString())
        skipBtn.visibility = if (canSkip) View.VISIBLE else View.GONE
        nextBtn.text = if(isLast) getString(R.string.Get_Started) else getString(R.string.Next)
        titleTextView.text = title
        descTextView.text = desc
        nextBtn.setOnClickListener {
        val nextItem = viewPager.currentItem + 1
        if (nextItem < (viewPager.adapter?.itemCount!!)) {
            viewPager.currentItem = nextItem
        } else {
            startActivity(Intent(this, LoginActivity::class.java) )
            finish()
        }
        }

        skipBtn.setOnClickListener {
            viewPager.currentItem = (viewPager.adapter?.itemCount ?: 0) - 1
        }
        }
    }
