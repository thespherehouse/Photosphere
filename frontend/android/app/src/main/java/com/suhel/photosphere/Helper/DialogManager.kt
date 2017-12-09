package com.suhel.photosphere.Helper

import android.app.DatePickerDialog
import android.content.Context
import android.support.v4.content.res.ResourcesCompat
import android.support.v7.app.AlertDialog
import android.view.LayoutInflater
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth
import com.suhel.photosphere.DataModels.Types.Derived.Competition
import com.suhel.photosphere.R
import java.util.*

fun dialogAddCompetition(context: Context) {

    val normalHintColor = ResourcesCompat.getColor(context.resources, R.color.theTranslucentDarkShade, null)
    val errorHintColor = ResourcesCompat.getColor(context.resources, R.color.theRedShade, null)

    val addView = LayoutInflater.from(context).inflate(R.layout.add_competition, null)
    val addName = addView.findViewById<EditText>(R.id.txtAddCompetitionName)
    val addDescription = addView.findViewById<EditText>(R.id.txtAddCompetitionDescription)
    val addDateStart = addView.findViewById<TextView>(R.id.lblAddCompetitionDateStart)
    val addDateEnd = addView.findViewById<TextView>(R.id.lblAddCompetitionDateEnd)
    val addCreate = addView.findViewById<Button>(R.id.bAddCompetitionCreate)
    val addCancel = addView.findViewById<Button>(R.id.bAddCompetitionCancel)

    var dateStart: Date? = null
    var dateEnd: Date? = null

    val dialog = AlertDialog.Builder(context).create()

    addDateStart.setOnClickListener {

        DatePickerDialog(context, {
            _, y, m, d ->

            val date = Calendar.getInstance()
            date.set(y, m, d)

            if (date <= Calendar.getInstance()) {

                addDateStart.setHintTextColor(errorHintColor)
                addDateStart.hint = "Must be after today"

            } else {

                if (dateEnd !== null && date.time > dateEnd) {

                    addDateStart.setHintTextColor(errorHintColor)
                    addDateStart.hint = "Must be before date end"

                } else {

                    addDateStart.setHintTextColor(normalHintColor)
                    addDateStart.hint = "Date Start"
                    addDateStart.text = "${d}/${m}/${y}"
                    dateStart = date.time
                }
            }


        }, Calendar.getInstance()[Calendar.YEAR],
                Calendar.getInstance()[Calendar.MONTH],
                Calendar.getInstance()[Calendar.DAY_OF_MONTH]).show()
    }

    addDateEnd.setOnClickListener {

        DatePickerDialog(context, {
            _, y, m, d ->

            val date = Calendar.getInstance()
            date.set(y, m, d)

            if (date <= Calendar.getInstance()) {

                addDateEnd.setHintTextColor(errorHintColor)
                addDateEnd.hint = "Must be after today"

            } else {

                if (dateStart !== null && date.time < dateStart) {

                    addDateEnd.setHintTextColor(errorHintColor)
                    addDateEnd.hint = "Must be after date start"

                } else {

                    addDateEnd.setHintTextColor(normalHintColor)
                    addDateEnd.hint = "Date Start"
                    addDateEnd.text = "${d}/${m}/${y}"
                    dateEnd = date.time
                }
            }


        }, Calendar.getInstance()[Calendar.YEAR],
                Calendar.getInstance()[Calendar.MONTH],
                Calendar.getInstance()[Calendar.DAY_OF_MONTH]).show()
    }

    addCreate.setOnClickListener {
        val name = addName.text.toString().trim()
        val description = addDescription.text.toString().trim()
        if (name == "") {

            addName.setHintTextColor(errorHintColor)
            addName.hint = "Name cannot be empty"

            return@setOnClickListener
        } else {

            addName.setHintTextColor(normalHintColor)
            addName.hint = "Name"

        }

        if (description == "") {

            addDescription.setHintTextColor(errorHintColor)
            addDescription.hint = "Description cannot be empty"

            return@setOnClickListener
        } else {

            addDescription.setHintTextColor(normalHintColor)
            addDescription.hint = "Description"

        }

        if (dateStart === null) {

            addDateStart.setHintTextColor(errorHintColor)
            addDateStart.hint = "Date start cannot be empty"

            return@setOnClickListener

        } else {

            addDateStart.setHintTextColor(normalHintColor)
            addDateStart.hint = "Date Start"

        }

        if (dateEnd === null) {

            addDateEnd.setHintTextColor(errorHintColor)
            addDateEnd.hint = "Date end cannot be empty"

            return@setOnClickListener

        } else {

            addDateEnd.setHintTextColor(normalHintColor)
            addDateEnd.hint = "Date End"

        }

        val competition = Competition()

        competition.name = name
        competition.description = description
        competition.dateCreated = Calendar.getInstance().time
        competition.dateStart = dateStart!!
        competition.dateEnd = dateEnd!!
        competition.hostId = FirebaseAuth.getInstance().currentUser?.uid ?: ""

        competition.insert()

        dialog.dismiss()
    }

    addCancel.setOnClickListener {
        dialog.dismiss()
    }

    dialog.setView(addView)
    dialog.show()
}