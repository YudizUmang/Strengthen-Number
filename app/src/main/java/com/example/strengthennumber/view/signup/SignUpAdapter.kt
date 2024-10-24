package com.example.strengthennumber.view.signup

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.strengthennumber.view.signup.fragments.SignUpFragment1
import com.example.strengthennumber.view.signup.fragments.SignUpFragment2
import com.example.strengthennumber.view.signup.fragments.SignUpFragment3

class SignUpAdapter(private val context: Context, fragmentManager: FragmentManager,
                    lifecycle: Lifecycle,) : FragmentStateAdapter(fragmentManager, lifecycle) {
    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0 -> SignUpFragment1(context)
            1 -> SignUpFragment2(context)
            2 -> SignUpFragment3(context)
            else -> throw IllegalStateException("Invalid Position: $position")
        }
    }


}