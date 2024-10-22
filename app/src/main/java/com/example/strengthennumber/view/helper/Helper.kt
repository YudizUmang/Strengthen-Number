package com.example.strengthennumber.view.helper

import android.content.Context
import android.view.View
import com.google.android.material.snackbar.Snackbar



class Helper {
    fun showSnackBar(context: Context, view : View, bgcolor : Int, message: String){
        val snackbar = Snackbar.make(view, message, Snackbar.LENGTH_SHORT)
            .setBackgroundTint(context.getColor(bgcolor))
            .setAnimationMode(Snackbar.ANIMATION_MODE_SLIDE)
            .setDuration(2000)
            .show()

    }
}