package com.example.myapplication

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class SupportRequestAdapter(private val supportRequests: List<SupportRequest>) :
    RecyclerView.Adapter<SupportRequestAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textViewReason: TextView = itemView.findViewById(R.id.textViewReason)
        val textViewDate: TextView = itemView.findViewById(R.id.textViewDate)
        val textViewTime: TextView = itemView.findViewById(R.id.textViewTime)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_support_request, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val supportRequest = supportRequests[position]
        holder.textViewReason.text = supportRequest.reason
        holder.textViewDate.text = "Date: ${supportRequest.year}/${supportRequest.month}/${supportRequest.day}"
        holder.textViewTime.text = "Time: ${supportRequest.hour}:${supportRequest.minute}"


    }

    override fun getItemCount(): Int {
        return supportRequests.size
    }
}
