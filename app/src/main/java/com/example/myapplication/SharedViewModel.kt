package com.example.myapplication

import androidx.lifecycle.ViewModel

class SharedViewModel : ViewModel() {

    private var selectedTask: Task? = null

    fun getSelectedTask(): Task? {
        return selectedTask
    }

    fun setSelectedTask(task: Task) {
        selectedTask = task
    }
}