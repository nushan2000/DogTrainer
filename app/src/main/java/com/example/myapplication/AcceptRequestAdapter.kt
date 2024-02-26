package com.example.myapplication

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DatabaseReference

class AcceptRequestAdapter(
    private val supportRequests: List<SupportRequest>,
    private val databaseReference: DatabaseReference,
    private val onDataChangedCallback: () -> Unit
) : RecyclerView.Adapter<AcceptRequestAdapter.ViewHolder>() {

    private val acceptedRequests: MutableList<AcceptedRequest> = mutableListOf()

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val buttonAccept: Button = itemView.findViewById(R.id.buttonAccept)
        val textViewReason: TextView = itemView.findViewById(R.id.textViewReason)
        val textViewDate: TextView = itemView.findViewById(R.id.textViewDate)
        val textViewTime: TextView = itemView.findViewById(R.id.textViewTime)


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_support_accept, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val supportRequest = supportRequests[position]
        holder.textViewReason.text = supportRequest.reason
        holder.textViewDate.text = "Date: ${supportRequest.year}/${supportRequest.month}/${supportRequest.day}"
        holder.textViewTime.text = "Time: ${supportRequest.hour}:${supportRequest.minute}"

        holder.buttonAccept.setOnClickListener {

            supportRequest.status = "accepted"


            val acceptedRequest = AcceptedRequest(
                id = supportRequest.id ?: "",
                reason = supportRequest.reason ?: "",
                year = supportRequest.year,
                month = supportRequest.month,
                day = supportRequest.day,
                hour = supportRequest.hour,
                minute = supportRequest.minute,
                status = "accepted"

            )


            Log.d("AcceptRequestAdapter", "AcceptedRequest Details:")
            Log.d("AcceptRequestAdapter", "ID: ${acceptedRequest.id}")
            Log.d("AcceptRequestAdapter", "Reason: ${acceptedRequest.reason}")
            Log.d("AcceptRequestAdapter", "Year: ${acceptedRequest.year}")
            Log.d("AcceptRequestAdapter", "Month: ${acceptedRequest.month}")
            Log.d("AcceptRequestAdapter", "Day: ${acceptedRequest.day}")
            Log.d("AcceptRequestAdapter", "Hour: ${acceptedRequest.hour}")
            Log.d("AcceptRequestAdapter", "Minute: ${acceptedRequest.minute}")
            Log.d("AcceptRequestAdapter", "Status: ${acceptedRequest.status}")


            acceptedRequests.add(acceptedRequest)


            notifyDataSetChanged()


            supportRequest.id?.let { requestId ->
                updateStatusInDatabase(requestId, "accepted")


                addAcceptedRequestToDatabase(acceptedRequest)



                deleteSupportRequestFromDatabase(requestId)
            }


            Log.d("AcceptRequestAdapter", "Updated Status: ${supportRequest.status}")
            Log.d("AcceptRequestAdapter", "Updated Reason: ${supportRequest.reason}")


            onDataChangedCallback.invoke()

            val context = holder.itemView.context
            val intent = Intent(context, TrainerHome::class.java)
            context.startActivity(intent)
        }


    }

    private fun updateStatusInDatabase(requestId: String, newStatus: String) {

        val requestRef = databaseReference.child("supportRequests").child(requestId)


        requestRef.child("status").setValue(newStatus)
    }

    private fun deleteSupportRequestFromDatabase( requestId: String) {

        val requestRef = databaseReference.child("supportRequests").child(requestId)


        requestRef.removeValue()
            .addOnSuccessListener {
                Log.d("AcceptRequestAdapter", "SupportRequest data deleted successfully")
            }
            .addOnFailureListener { e ->
                Log.e("AcceptRequestAdapter", "Failed to delete SupportRequest data. Error: $e")
            }
    }

    private fun addAcceptedRequestToDatabase(acceptedRequest: AcceptedRequest) {

        val acceptedRequestsRef = databaseReference.child("acceptedRequests").push()


        acceptedRequestsRef.setValue(acceptedRequest)
            .addOnSuccessListener {
                Log.d("AcceptRequestAdapter", "AcceptedRequest data added successfully")
            }
            .addOnFailureListener { e ->
                Log.e("AcceptRequestAdapter", "Failed to add AcceptedRequest data. Error: $e")
            }
    }

    override fun getItemCount(): Int {
        return supportRequests.size
    }
}
