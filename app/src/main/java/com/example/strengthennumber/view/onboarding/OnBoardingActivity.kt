package com.example.strengthennumber.view.onboarding

import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
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
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_onboarding)

        val onBoardList = listOf<Fragment>(
            OnBoarding(
                getDrawableFromImg(R.drawable.on_board1),
                getString(R.string.onboarding_title1),
                getString(R.string.onboarding_desc1),
                true,
                false,
                this
            ),
            OnBoarding(
                getDrawableFromImg(R.drawable.on_board2),
                getString(R.string.onboarding_title2),
                getString(R.string.onboarding_desc2),
                true,
                false,
                this
            ),
            OnBoarding(
                getDrawableFromImg(R.drawable.on_board3),
                getString(R.string.onboarding_title3),
                getString(R.string.onboarding_desc3),
                false,
                true,
                this
            )
        )
        tabLayout = findViewById(R.id.into_tab_layout)
        viewPager = findViewById(R.id.onboarding_viewPager)
        adapter = ViewPagerAdapter(onBoardList, supportFragmentManager, lifecycle)
        viewPager.adapter = adapter
        TabLayoutMediator(tabLayout, viewPager) { _, _ ->
        }.attach()
    }

    private fun getDrawableFromImg(id: Int): Drawable? {
        return AppCompatResources.getDrawable(this, id) ?: AppCompatResources.getDrawable(
            this,
            R.drawable.logo
        )
    }

    override fun skipBtnClick() {
        viewPager.currentItem = (viewPager.adapter?.itemCount ?: 0) - 1
    }

    override fun nextBtnClick(text: String) {
        val nextItem = viewPager.currentItem + 1
        if (nextItem < (viewPager.adapter?.itemCount ?: 0)) {
            viewPager.currentItem = nextItem
        } else {
            startActivity(Intent(this, LoginActivity::class.java) )
            finish()
        }
    }
}