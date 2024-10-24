package com.example.strengthennumber.view.signup.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.GridView
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import com.example.strengthennumber.R
import com.example.strengthennumber.view.helper.Helper
import com.example.strengthennumber.view.signup.SignUpActivity
import com.example.strengthennumber.view.signup.fragments.grid.GridAdapter
import com.example.strengthennumber.view.signup.fragments.grid.GridItem
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.textfield.TextInputEditText
import com.google.gson.JsonArray
import com.google.gson.JsonObject

class SignUpFragment3(private val context: Context) : Fragment() {
    private lateinit var adapter: GridAdapter
    private lateinit var choiceGridView : GridView
    private lateinit var fitnessLevel : TextInputEditText
    private lateinit var fitnessErrText : TextView
    val chosenInterest = mutableSetOf<String>()
    private lateinit var dataPasser: PassData
    private val helper = Helper()

    private val itemList = listOf<GridItem>(
        GridItem(R.drawable.gym, R.drawable.gym_light, context.getString(R.string.gym)),
        GridItem(R.drawable.yoga,R.drawable.yoga_light, context.getString(R.string.yoga)),
        GridItem(R.drawable.cardio,R.drawable.cardio_light, context.getString(R.string.cardio)),
        GridItem(R.drawable.home_workout,R.drawable.home_workout_light, context.getString(R.string.workout)),
        GridItem(R.drawable.cycling,R.drawable.cycling_light, context.getString(R.string.cycling)),
        GridItem(R.drawable.zumba, R.drawable.zumba_light,context.getString(R.string.zumba)),
        GridItem(R.drawable.diet,R.drawable.diet_light, context.getString(R.string.dieting)),
        GridItem(R.drawable.sports, R.drawable.sports_light, context.getString(R.string.sports)),
        GridItem(R.drawable.running, R.drawable.running_light, context.getString(R.string.running)),

    )

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
        val view  = inflater.inflate(R.layout.fragment_sign_up3, container, false)
        choiceGridView = view.findViewById(R.id.choice_grid_view)
        fitnessErrText = view.findViewById(R.id.error_text_user_fitness)
        adapter = GridAdapter(context, itemList)
        choiceGridView.adapter = adapter

        choiceGridView.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->

           itemList[position].isSelected = !itemList[position].isSelected
            if(itemList[position].isSelected) {
                chosenInterest.add(itemList[position].text)
            }
            else {
                chosenInterest.remove(itemList[position].text)
            }

            Log.d("Item Selected", chosenInterest.toString())
            adapter.notifyDataSetChanged()
        }

        fitnessLevel = view.findViewById(R.id.signup_user_fitness)

        fitnessLevel.setOnClickListener {
            showBottomSheet()
        }
        return view
    }

    private fun showBottomSheet() {
        val bottomSheetDialog = BottomSheetDialog(requireContext())
        val bottomSheetView = LayoutInflater.from(requireContext())
            .inflate(R.layout.fitness_bottom_sheet, null)
        val radioButton = bottomSheetView.findViewById<RadioGroup>(R.id.fitness_radio_btn)

        radioButton.setOnCheckedChangeListener { _, checkedId ->
            val selectedRadioButton = bottomSheetView.findViewById<RadioButton>(checkedId)
            val selectedText = selectedRadioButton?.text.toString()

            fitnessLevel.setText(selectedText)

            bottomSheetDialog.dismiss()
        }

        bottomSheetDialog.setContentView(bottomSheetView)
        bottomSheetDialog.show()
    }

    fun onButtonClick() {
        if(fitnessLevel.text?.isEmpty() == false) {
            if(chosenInterest.size < 3){
                helper.showSnackBar(
                    context,
                    requireView(),
                    R.color.errorColor,
                    context.getString(R.string.interest_err)
                )
            }else{

            val data = JsonObject()
            val jsonArray = JsonArray()

            for (interest in chosenInterest)
                jsonArray.add(interest)

            data.addProperty("fitness_level", fitnessLevel.text.toString().lowercase().trim())
            data.add("interests", jsonArray)

            dataPasser.onDataPass(data)
            }
        }else{
            fitnessErrText.visibility = View.VISIBLE
            fitnessErrText.text = context.getString(R.string.fitness_err)
        }
    }

}