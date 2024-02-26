package com.example.myapplication

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class TaskRequestAdapter(private val task: List<Task>) :
    RecyclerView.Adapter<TaskRequestAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textViewTitle: TextView = itemView.findViewById(R.id.textViewTitle)
        val textViewName: TextView = itemView.findViewById(R.id.textViewName)

        val textViewModel: TextView = itemView.findViewById(R.id.textViewModel)


    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskRequestAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_task_request, parent, false)
        return TaskRequestAdapter.ViewHolder(view)
    }
    override fun onBindViewHolder(holder: TaskRequestAdapter.ViewHolder, position: Int) {
        val taskRequest = task[position]
        holder.textViewTitle.text = taskRequest.taskTitle
        holder.textViewName.text =taskRequest.taskName

        holder.textViewModel.text =taskRequest.taskModel



    }
    override fun getItemCount(): Int {
        return task.size
    }
    }