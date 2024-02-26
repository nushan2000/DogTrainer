package com.example.myapplication.ui.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.myapplication.Task
import com.example.myapplication.TaskDetails
import com.example.myapplication.databinding.FragmentHomeBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding

    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference
    private val tasks: MutableList<Task> = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentHomeBinding.inflate(inflater, container, false)
        auth = FirebaseAuth.getInstance()
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        database = FirebaseDatabase.getInstance().reference

        fetchTasks()
        binding.task1Button.setOnClickListener {
            Toast.makeText(requireContext(), "Task 1 Button Clicked", Toast.LENGTH_SHORT).show()
            Log.d("FragmentHome", "Button Clicked")
            onTaskButtonClick(0)

        }
        binding.task2Button.setOnClickListener {
            onTaskButtonClick(1)
        }
        binding.task3Button.setOnClickListener {
            onTaskButtonClick(2)
                  }
        binding.task4Button.setOnClickListener {
            onTaskButtonClick(3)
    }
//
    }
    private fun fetchTasks() {
        val userId = auth.currentUser?.uid

        userId?.let {
            database.child("tasks")
                .addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        tasks.clear()

                        for (taskSnapshot in snapshot.children) {
                            val task = taskSnapshot.getValue(Task::class.java)
                            task?.let {
                                tasks.add(it)
                            }
                        }
                        Log.d("FragmentHome", "Number of tasks fetched: ${tasks.size}")

                    }

                    override fun onCancelled(error: DatabaseError) {

                    }
                })
        }
    }
    private fun onTaskButtonClick(position: Int) {
        Log.d("FragmentHome", "onTaskButtonClick called with position: $position, tasks size: ${tasks.size}")
        if (position < tasks.size) {
            val selectedTask = tasks[position]
            Log.d("FragmentHome", "Selected Task: $selectedTask")

            val intent = Intent(activity, TaskDetails::class.java)
            intent.putExtra("selectedTask", selectedTask)
            startActivity(intent)
        }else{
            Log.e("FragmentHome", "Invalid position: $position. Tasks size: ${tasks.size}")
            for ((index, task) in tasks.withIndex()) {
                Log.d("FragmentHome", "Task at position $index: $task")
            }
        }
    }


}