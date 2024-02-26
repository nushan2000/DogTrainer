package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class TrainerHome : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_trainer_home)

        val button4= findViewById<Button>(R.id.button4)
        button4.setOnClickListener {
            val intent = Intent(this@TrainerHome, AllSupportRequestActivity::class.java)
            startActivity(intent)
        }

        val button5= findViewById<Button>(R.id.button5)
        button5.setOnClickListener {
            val intent = Intent(this@TrainerHome, AllAcceptRequest::class.java)
            startActivity(intent)
        }
    }
}
