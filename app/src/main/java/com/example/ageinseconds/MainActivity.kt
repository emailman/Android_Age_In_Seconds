package com.example.ageinseconds

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {
    // Declare variables for GUI widgets
    private lateinit var txtDateSelected: TextView
    private lateinit var btnSelectDate: Button
    private lateinit var txtMinutesOld: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Assign variable for GUI widgets
        txtDateSelected = findViewById(R.id.txtDateSelected)
        btnSelectDate = findViewById(R.id.btnSelectDate)
        txtMinutesOld = findViewById(R.id.txtMinutesOld)

        // Event handler for button
        btnSelectDate.setOnClickListener {
            clickSelectDate()
        }
    }

    private fun clickSelectDate() {
        // Create a calendar object
        val myCalendar = Calendar.getInstance()
        val year = myCalendar.get(Calendar.YEAR)
        val month = myCalendar.get(Calendar.MONTH)
        val day = myCalendar.get(Calendar.DAY_OF_MONTH)

        // Create an object for the Date Picker dialog
        val datePickerDialog = DatePickerDialog(this,
            {
                // Define event handler for  Date Picker Dialog
                    _, pickedYear, pickedMonth, pickedDay ->
                val dateSelected = "${pickedMonth + 1}/" +
                        "$pickedDay/$pickedYear"
                txtDateSelected.text = dateSelected

                // Convert selected date to SDF
                val sdf = SimpleDateFormat("MM/dd/yyyy",
                    Locale.ENGLISH)
                val selectedDate = sdf.parse(dateSelected)
                // Only process if selected date is not null
                selectedDate?.let {
                    // Calculate the selected date in minutes
                    val selectedDateInMinutes =
                        selectedDate.time / 60_000

                    // Get current date and calculate the value in minutes
                    val currentDate = sdf.parse(sdf.format(
                        (System.currentTimeMillis())))
                    // Only process if current date is not null
                    currentDate?.let {
                        val currentDateInMinutes =
                            currentDate.time / 60_000

                        // Calculate and show the elapsed minutes between
                        // the selected date and the current date
                        val ageInMinutes =
                            currentDateInMinutes - selectedDateInMinutes
                        txtMinutesOld.text = "%,d".format(ageInMinutes)
                    }
                }
            }
            , year, month, day)

        // Limit the date picker dialog to dates in the past
        // Subtract 1 day in milliseconds from the current date
        datePickerDialog.datePicker.maxDate =
            System.currentTimeMillis() - 86_400_000
        datePickerDialog.show()
    }
}