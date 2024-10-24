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
import android.widget.TextView
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import com.example.strengthennumber.R
import com.example.strengthennumber.repository.remote.Data
import com.example.strengthennumber.repository.remote.UserResponse
import com.example.strengthennumber.view.signup.SignUpActivity
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.textfield.TextInputEditText
import com.google.gson.JsonObject


class SignUpFragment2(private val context: Context) : Fragment() {

    private lateinit var imageSelected : ImageView
    private lateinit var userName : TextInputEditText
    private lateinit var userGender : TextInputEditText
    private lateinit var userBio : TextInputEditText
    private lateinit var imageResultLauncher: ActivityResultLauncher<Intent>
    private lateinit var dataPasser: PassData
    private lateinit var useridErr :  TextView
    private lateinit var userGenderErr : TextView
    private lateinit var userBioErr : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }
    override fun onAttach(context: Context) {
        super.onAttach(context)
        if(context is SignUpActivity){
            dataPasser = context
        }
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
        useridErr = view.findViewById(R.id.error_text_user_id)
        userBioErr = view.findViewById(R.id.error_text_user_bio)
        userGenderErr = view.findViewById(R.id.error_text_user_gender)

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

    fun onButtonClick() {

        if(validateFields(userName.text.toString(), userGender.text.toString(), userBio.text.toString())){
            val data = JsonObject()
            data.addProperty("username", userName.text.toString().trim())
            data.addProperty("gender", userGender.text.toString().lowercase().trim())
            data.addProperty("bio", userBio.text.toString().trim())
            dataPasser.onDataPass(data)
        }
    }

    private fun validateFields(username: String, gender: String, bio: String): Boolean {
        var isValid = true

        useridErr.visibility = View.INVISIBLE
        userGenderErr.visibility = View.INVISIBLE
        userBioErr.visibility = View.INVISIBLE

        if (username.isEmpty()) {
            useridErr.visibility = View.VISIBLE
            useridErr.text = context.getString(R.string.usernm_id_err)
            isValid = false
        }

        if (gender.isEmpty()) {
            userGenderErr.visibility = View.VISIBLE
            userGenderErr.text = context.getString(R.string.gender_err)
            isValid = false
        }

        if (bio.isEmpty()) {
            userBioErr.visibility = View.VISIBLE
            userBioErr.text = context.getString(R.string.bio_err)
            isValid = false
        }
        return isValid
    }

}