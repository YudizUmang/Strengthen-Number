package com.example.strengthennumber.view.signup.fragments

import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import androidx.fragment.app.Fragment
import com.example.strengthennumber.R
import com.google.android.material.textfield.TextInputEditText
import java.util.Calendar


class SignUpFragment1() : Fragment() {

private lateinit var datePicker : TextInputEditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_sign_up1, container, false)
        datePicker = view.findViewById(R.id.signup_user_dob)

        datePicker.setOnClickListener{
            openDatePickerDialog()
        }
        return view
    }

    private fun openDatePickerDialog() {
        val calendar: Calendar = Calendar.getInstance()
        val year: Int = calendar.get(Calendar.YEAR)
        val month: Int = calendar.get(Calendar.MONTH)
        val day: Int = calendar.get(Calendar.DAY_OF_MONTH)
        calendar.add(Calendar.YEAR, -18)
        val datePickerDialog = DatePickerDialog(
            requireContext(),
            { _: DatePicker?, selectedYear: Int, selectedMonth: Int, selectedDay: Int ->
                val date =
                    selectedDay.toString() + "-" + (selectedMonth + 1) + "-" + selectedYear
                datePicker.setText(date)
            },
            year,
            month,
            day
        )
        datePickerDialog.datePicker.maxDate = calendar.timeInMillis;

        datePickerDialog.show()
    }


}