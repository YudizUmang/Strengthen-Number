package com.example.strengthennumber.view.onboarding

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.strengthennumber.R

interface OnBoardingButtons{
    fun skipBtnClick()
    fun nextBtnClick(text : String)
}

class OnBoarding(
    private val img: Drawable?,
    private val title: String,
    private val desc: String,
    private val canSkip: Boolean = true,
    private val isLast: Boolean = false,
    private val listener : OnBoardingButtons? = null
) : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_on_boarding, container, false)
        val imgView = view.findViewById<ImageView>(R.id.onboard_img)
        imgView.setImageDrawable(img)
        val textviewTitle = view.findViewById<TextView>(R.id.onboard_title)
        textviewTitle.text = title
        val textviewDesc = view.findViewById<TextView>(R.id.onboard_desc)
        textviewDesc.text = desc
        val skipBtn = view.findViewById<Button>(R.id.skip_btn)
        if (canSkip) {
            skipBtn.visibility = View.VISIBLE
            skipBtn.setOnClickListener {
                listener?.skipBtnClick()
            }
        } else {
            skipBtn.visibility = View.GONE
        }

        val nextBtn = view.findViewById<Button>(R.id.next_btn)
        if (isLast) {
            nextBtn.text = getString(R.string.Get_Started)
        } else {
            nextBtn.text = getString(R.string.Next)
        }
        nextBtn.setOnClickListener {
            listener?.nextBtnClick(nextBtn.text as String)
        }

       return view
    }

}