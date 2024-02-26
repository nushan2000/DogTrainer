package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.DatePicker
import android.widget.EditText
import android.widget.TimePicker
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class SupportTranner : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_support_tranner)

        FirebaseApp.initializeApp(this)


        val userId = FirebaseAuth.getInstance().currentUser?.uid

        if (userId != null) {

            val databaseReference = FirebaseDatabase.getInstance().getReference("supportRequests/$userId")

            val editTextReason = findViewById<EditText>(R.id.editTextReason)
            val datePicker = findViewById<DatePicker>(R.id.datePicker1)
            val timePicker = findViewById<TimePicker>(R.id.timePicker)
            val btnApply = findViewById<Button>(R.id.btnApply)

            btnApply.setOnClickListener {

            val reason = editTextReason.text.toString()

            val year = datePicker.year
            val month = datePicker.month + 1
            val day = datePicker.dayOfMonth
            val hour = timePicker.hour
            val minute = timePicker.minute


                val supportRequest = SupportRequest(
                    id = null,
                    userId=null,

                    reason = reason,
                    year = year,
                    month = month,
                    day = day,
                    hour = hour,
                    minute = minute,
                    status = "notaccept"

                )
                val newReference = databaseReference.push()
                newReference.setValue(supportRequest.copy(id = newReference.key))

                Toast.makeText(this, "Support request submitted!", Toast.LENGTH_SHORT).show()

                val intent = Intent(this@SupportTranner, TaskDetails::class.java)
                startActivity(intent)
                finish()
            }
        } else {

            println("User not authenticated")
        }
    }
}
