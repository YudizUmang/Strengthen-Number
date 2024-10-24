package com.example.strengthennumber.view.signup.fragments

import android.app.DatePickerDialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.DatePicker
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.strengthennumber.R
import com.example.strengthennumber.repository.remote.Data
import com.example.strengthennumber.repository.remote.UserResponse
import com.example.strengthennumber.view.helper.Helper
import com.example.strengthennumber.view.signup.SignUpActivity
import com.example.strengthennumber.viewmodel.signupviewmodel.SignUpViewModel
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.DateValidatorPointBackward
import com.google.android.material.datepicker.DateValidatorPointForward
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.textfield.TextInputEditText
import com.google.gson.JsonObject
import org.intellij.lang.annotations.JdkConstants.BoxLayoutAxis
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.Calendar

class SignUpFragment1(private val context: Context) : Fragment() {
    private lateinit var datePickerEditText : TextInputEditText
    private lateinit var tcCheckBox : CheckBox
    private lateinit var ageCheckBox: CheckBox
    private lateinit var fullNameEditText : EditText
    private lateinit var emailEditText: EditText
    private lateinit var dataPasser: PassData
    private lateinit var userNmErr : TextView
    private lateinit var userEmailErr : TextView
    private lateinit var userDobErr : TextView
    private val helper = Helper()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if(context is SignUpActivity){
            dataPasser = context
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_sign_up1, container, false)
        datePickerEditText= view.findViewById(R.id.signup_user_dob)
        tcCheckBox = view.findViewById(R.id.tc_checkbox)
        ageCheckBox = view.findViewById(R.id.age_checkbox)
        fullNameEditText = view.findViewById(R.id.signup_user_nm)
        emailEditText = view.findViewById(R.id.signup_user_email)
        userNmErr = view.findViewById(R.id.error_text_user_nm)
        userEmailErr = view.findViewById(R.id.error_text_user_email)
        userDobErr = view.findViewById(R.id.error_text_user_dob)

        datePickerEditText.setOnClickListener{
            openDatePickerDialog()
        }
        return view
    }

    private fun openDatePickerDialog() {
        val calendar: Calendar = Calendar.getInstance()

        calendar.add(Calendar.YEAR, -18)
        val maxDateInMillis = calendar.timeInMillis
        val datePickerBuilder = MaterialDatePicker.Builder.datePicker()
        
        datePickerBuilder.setTitleText("Select your date of birth")
        datePickerBuilder.setSelection(maxDateInMillis)
        datePickerBuilder.setCalendarConstraints(
            CalendarConstraints.Builder()
                .setValidator(DateValidatorPointBackward.before(maxDateInMillis))
                .setEnd(maxDateInMillis)
                .build()
        )

        val datePicker = datePickerBuilder.build()

        datePicker.addOnPositiveButtonClickListener { selection ->
            val selectedDate = LocalDateTime.ofInstant(
                Instant.ofEpochMilli(selection),
                ZoneId.systemDefault()
            )
            val formattedDate = "${selectedDate.year}-${String.format("%02d", selectedDate.monthValue)}-${String.format("%02d", selectedDate.dayOfMonth)}"
            datePickerEditText.setText(formattedDate)
        }

        datePicker.show(requireActivity().supportFragmentManager, "MATERIAL_DATE_PICKER")

    }

    fun onButtonClick() {
        if(validateFields(fullNameEditText.text.toString(), emailEditText.text.toString().trim(), datePickerEditText.text.toString()) && agree(tcCheckBox.isChecked, ageCheckBox.isChecked)) {
            val data = JsonObject()
            data.addProperty("name", fullNameEditText.text.toString().trim())
            data.addProperty("email", emailEditText.text.toString().trim())
            data.addProperty("dob", datePickerEditText.text.toString().trim())
            dataPasser.onDataPass(data)
        }
    }


    private fun validateFields(name: String, email: String, dob: String): Boolean {
        var isValid = true

        userNmErr.visibility = View.INVISIBLE
        userEmailErr.visibility = View.INVISIBLE
        userDobErr.visibility = View.INVISIBLE

         if (name.isEmpty()) {
            userNmErr.visibility = View.VISIBLE
            userNmErr.text = context.getString(R.string.usernm_err)
            isValid = false
        }

        if (email.isEmpty() || !isValidEmail(email)) {
            userEmailErr.visibility = View.VISIBLE
            userEmailErr.text = context.getString(R.string.email_err)
            isValid = false
        }

        if (dob.isEmpty()) {
            userDobErr.visibility = View.VISIBLE
            userDobErr.text = context.getString(R.string.dob_err)
            isValid = false
        }
        return isValid
    }

    private fun agree(tcBox : Boolean,ageBox : Boolean) : Boolean{
        if (tcBox == false) {
            helper.showSnackBar(context, requireView(), R.color.errorColor, context.getString(R.string.tc_err))
            return false
        }

        if (ageBox == false) {
            helper.showSnackBar(context, requireView(), R.color.errorColor, context.getString(R.string.age_err))
            return false
        }
        return true
    }

    private fun isValidEmail(email : String) : Boolean{
        return email.matches("[a-zA-Z0-9._-]+@[a-z]+\\.[a-z]+".toRegex())
    }

}
