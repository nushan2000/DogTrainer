package com.example.myapplication.ui.notifications

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R


import com.example.myapplication.Task
import com.example.myapplication.TaskRequestAdapter
import com.example.myapplication.databinding.FragmentNotificationsBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class NotificationsFragment : Fragment() {

    private lateinit var binding: FragmentNotificationsBinding
    private lateinit var completedTasksList: MutableList<Task>
    private lateinit var recyclerView: RecyclerView
    private lateinit var completedTasksAdapter: TaskRequestAdapter


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNotificationsBinding.inflate(inflater, container, false)
        val root: View = binding.root


        recyclerView = root.findViewById(R.id.recyclerViews)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())


        completedTasksList = mutableListOf()
        completedTasksAdapter = TaskRequestAdapter(completedTasksList)
        recyclerView.adapter = completedTasksAdapter


        val userId = FirebaseAuth.getInstance().currentUser?.uid

        if (userId != null) {

            val completedTasksReference =FirebaseDatabase.getInstance().getReference("completedTasks/$userId")

            completedTasksReference.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {

                    completedTasksList.clear()
                    for (childSnapshot in dataSnapshot.children) {
                        val completedTask = childSnapshot.getValue(Task::class.java)
                        completedTask?.let {

                            if (it.taskStatus == "completed") {
                                completedTasksList.add(it)
                            }
                        }


                        completedTasksAdapter.notifyDataSetChanged()
                    }
                }
                override fun onCancelled(databaseError: DatabaseError) {

                }
            })
        } else {

            println("User not authenticated")
        }

        return root
    }


}

