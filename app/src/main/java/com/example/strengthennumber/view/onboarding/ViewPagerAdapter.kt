package com.example.strengthennumber.view.onboarding

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter


class ViewPagerAdapter(
    private val onBoardList: List<Fragment>, fragmentManager: FragmentManager,
    lifecycle: Lifecycle,
) : FragmentStateAdapter(fragmentManager, lifecycle) {

    override fun getItemCount(): Int {
        return onBoardList.size
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> onBoardList[position]
            1 -> onBoardList[position]
            2 -> onBoardList[position]
            else -> throw IllegalStateException("Inavlid position: $position")
        }
    }


}