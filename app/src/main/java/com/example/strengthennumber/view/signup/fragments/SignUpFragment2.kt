package com.example.strengthennumber.view.signup.fragments

import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import com.example.strengthennumber.R
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.textfield.TextInputEditText


class SignUpFragment2() : Fragment() {
    private lateinit var imageSelected : ImageView
    private lateinit var userName : TextInputEditText
    private lateinit var userGender : TextInputEditText
    private lateinit var userBio : TextInputEditText
    private lateinit var imageResultLauncher: ActivityResultLauncher<Intent>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_sign_up2, container, false)
        userName = view.findViewById(R.id.signup_user_nm_id)
        userBio = view.findViewById(R.id.signup_user_bio)
        imageSelected = view.findViewById(R.id.profile_img)
        userGender = view.findViewById(R.id.signup_user_gender)
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

        userGender.setOnClickListener {
            showBottomSheet()
        }
        return view
    }
    private fun showBottomSheet() {
        val bottomSheetDialog = BottomSheetDialog(requireContext())
        val bottomSheetView = LayoutInflater.from(requireContext())
            .inflate(R.layout.gender_bottom_sheet, null)
        val radioButton = bottomSheetView.findViewById<RadioGroup>(R.id.gender_radio_btn)

        radioButton.setOnCheckedChangeListener { _, checkedId ->
            val selectedRadioButton = bottomSheetView.findViewById<RadioButton>(checkedId)
            val selectedText = selectedRadioButton?.text.toString()

            userGender.setText(selectedText)

            bottomSheetDialog.dismiss()
        }

        bottomSheetDialog.setContentView(bottomSheetView)
        bottomSheetDialog.show()
    }

}