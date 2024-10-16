package com.example.strengthennumber.view.onboarding

import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.example.strengthennumber.R

class OnBoardingActivity : AppCompatActivity() {
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
                false
            ),
            OnBoarding(
                getDrawableFromImg(R.drawable.on_board2),
                getString(R.string.onboarding_title2),
                getString(R.string.onboarding_desc2),
                true,
                false
            ),
            OnBoarding(
                getDrawableFromImg(R.drawable.on_board3),
                getString(R.string.onboarding_title3),
                getString(R.string.onboarding_desc3),
                false,
                true
            )
        )

        val viewpager = findViewById<ViewPager2>(R.id.onboarding_viewPager)
        val adapter = ViewPagerAdapter(onBoardList, supportFragmentManager, lifecycle)
        viewpager.adapter = adapter
    }

    private fun getDrawableFromImg(id: Int): Drawable? {
        return AppCompatResources.getDrawable(this, id) ?: AppCompatResources.getDrawable(
            this,
            R.drawable.logo
        )
    }
}