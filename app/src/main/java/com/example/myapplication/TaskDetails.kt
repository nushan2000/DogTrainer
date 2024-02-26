package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.databinding.ActivityTaskDetailsBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase


@Suppress("DEPRECATION")
class TaskDetails : AppCompatActivity() {

    private lateinit var binding: ActivityTaskDetailsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTaskDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)



        val selectedTask = intent.getParcelableExtra<Task>("selectedTask")
        if (selectedTask != null) {

        binding.textViewTaskTitle.text = selectedTask?.taskTitle
        binding.textViewTaskModel.text = selectedTask?.taskModel
        binding.textViewTaskName.text = "Task Name: ${selectedTask?.taskName}"
        binding.textViewTaskDetails.text = "Task Details: ${selectedTask?.taskDetails}"


            binding.btnDone.setOnClickListener {

                selectedTask?.let { task ->
                    val userId = FirebaseAuth.getInstance().currentUser?.uid
                    if (userId != null) {
                        val completedTasksReference =
                            FirebaseDatabase.getInstance().getReference("completedTasks/$userId")
                        task.taskStatus = "completed"

                        completedTasksReference.push().setValue(task)
                    }
                    val intent = Intent(this, MainActivity3::class.java)
                    startActivity(intent)
                }



            }
            binding.btnGetSupport.setOnClickListener {

                val intent = Intent(this, SupportTranner::class.java)
                startActivity(intent)
            }
        }
        else {
                   Log.e("TaskDetails", "Selected task is null.")




               }




    }
}