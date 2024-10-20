package com.example.strengthennumber.view.signup.fragments

import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.example.strengthennumber.R
import com.google.android.material.textfield.TextInputEditText


class SignUpFragment2(private val ctx : Context) : Fragment() {
    private lateinit var imageSelected : ImageView
//    private lateinit var userName : TextInputEditText
//    private lateinit var userGender : TextInputEditText
//    private lateinit var userBio : TextInputEditText
    private lateinit var imageResultLauncher: ActivityResultLauncher<Intent>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_sign_up2, container, false)
        imageSelected = view.findViewById(R.id.profile_img)
        imageResultLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == RESULT_OK) {
                    val imageUri = result.data?.data!!
                    imageSelected.setImageURI(imageUri)
                }
            }

        imageSelected.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            imageResultLauncher.launch(intent)
        }
        return view
    }


}