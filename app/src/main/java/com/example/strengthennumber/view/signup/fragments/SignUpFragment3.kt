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
import com.example.strengthennumber.R
import com.example.strengthennumber.view.signup.fragments.grid.GridAdapter
import com.example.strengthennumber.view.signup.fragments.grid.GridItem
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.textfield.TextInputEditText

class SignUpFragment3(private val context: Context) : Fragment() {
    private lateinit var adapter: GridAdapter
    private lateinit var choiceGridView : GridView
    private lateinit var fitnessLevel : TextInputEditText
    val chosenInterest = mutableSetOf<String>()

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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view  = inflater.inflate(R.layout.fragment_sign_up3, container, false)
        choiceGridView = view.findViewById(R.id.choice_grid_view)
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

}