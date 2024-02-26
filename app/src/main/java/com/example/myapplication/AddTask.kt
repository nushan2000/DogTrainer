package com.example.myapplication

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class AddTask : AppCompatActivity() {
    private lateinit var taskTitleEditText: EditText
    private lateinit var taskModelEditText: EditText
    private lateinit var taskNameEditText: EditText
    private lateinit var taskDetailsEditText: EditText
    private lateinit var addTaskButton: Button

    private lateinit var database: DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_task)

        taskTitleEditText = findViewById(R.id.editTextTaskTitle)
        taskModelEditText = findViewById(R.id.editTextTaskModel)
        taskNameEditText = findViewById(R.id.editTextTaskName)
        taskDetailsEditText = findViewById(R.id.editTextTaskDetails)
        addTaskButton = findViewById(R.id.buttonAddTask)

        database = FirebaseDatabase.getInstance().reference


        addTaskButton.setOnClickListener {
            addTaskToFirebase()
        }
    }

    private fun addTaskToFirebase() {

            val taskId = database.child("tasks").push().key
            val task = Task(
                taskId.hashCode(),
                taskTitleEditText.text.toString(),
                taskModelEditText.text.toString(),
                taskNameEditText.text.toString(),
                taskDetailsEditText.text.toString(),
                emptyList(),
                "Pending"
            )

            database.child("tasks").child(taskId!!).setValue(task)
            finish()

    }
}