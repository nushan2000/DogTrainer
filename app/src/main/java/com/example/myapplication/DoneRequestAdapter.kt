package com.example.myapplication

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DatabaseReference

class DoneRequestAdapter(
    private val supportRequests: MutableList<SupportRequest>,
    private var databaseReference: DatabaseReference
) : RecyclerView.Adapter<DoneRequestAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textViewReason: TextView = itemView.findViewById(R.id.textViewReason)
        val textViewDate: TextView = itemView.findViewById(R.id.textViewDate)
        val textViewTime: TextView = itemView.findViewById(R.id.textViewTime)
        val buttonDo: Button = itemView.findViewById(R.id.buttonDo)
        val messageSection: LinearLayout = itemView.findViewById(R.id.messageSection)
        val editTextMessage: EditText = itemView.findViewById(R.id.editTextMessage)
        val buttonSend: Button = itemView.findViewById(R.id.buttonSend)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_support_done, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val supportRequest = supportRequests[position]
        holder.textViewReason.text = supportRequest.reason
        holder.textViewDate.text =
            "Date: ${supportRequest.year}/${supportRequest.month}/${supportRequest.day}"
        holder.textViewTime.text = "Time: ${supportRequest.hour}:${supportRequest.minute}"

        holder.buttonDo.setOnClickListener {
            Toast.makeText(holder.itemView.context, "Button Done clicked", Toast.LENGTH_SHORT)
                .show()


            holder.messageSection.visibility = if (holder.messageSection.visibility == View.VISIBLE) {
                View.GONE
            } else {
                View.VISIBLE
            }
        }

        holder.buttonSend.setOnClickListener {

            val message = holder.editTextMessage.text.toString()
            if (message.isNotEmpty()) {

                updateMessageInDatabase(supportRequest.id, message)


                supportRequest.message = message


                notifyDataSetChanged()
            }
        }
    }

    override fun getItemCount(): Int {
        return supportRequests.size
    }

    private fun updateMessageInDatabase(requestId: String?, message: String) {
        val requestRef = databaseReference.child("supportRequests").child(requestId ?: "")


        requestRef.child("message").setValue(message)
            .addOnSuccessListener {
                Log.d("DoneRequestAdapter", "Message updated successfully")
            }
            .addOnFailureListener { e ->
                Log.e("DoneRequestAdapter", "Failed to update message. Error: $e")
            }
    }
}
