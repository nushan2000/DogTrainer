package com.example.myapplication


import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.SupportRequest
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class AllAcceptRequest : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var doneRequestAdapter: DoneRequestAdapter
    private lateinit var acceptRequestList: MutableList<SupportRequest>


    private val databaseReference: DatabaseReference =
        FirebaseDatabase.getInstance().getReference("supportRequests")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_trainer_dashboard)


        recyclerView = findViewById(R.id.recyclerViewAccepted)
        recyclerView.layoutManager = LinearLayoutManager(this)


        acceptRequestList = mutableListOf()
        doneRequestAdapter = DoneRequestAdapter(acceptRequestList,databaseReference)
        recyclerView.adapter = doneRequestAdapter


        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                Log.d("Firebase Database", "onDataChange triggered")

                acceptRequestList.clear()


                for (userSnapshot in dataSnapshot.children) {
                    for (childSnapshot in userSnapshot.children) {
                        val request = childSnapshot.getValue(SupportRequest::class.java)
                        if (request?.status == "accepted") {
                            acceptRequestList.add(request)
                        }
                    }
                }

                doneRequestAdapter.notifyDataSetChanged()
            }

            override fun onCancelled(databaseError: DatabaseError) {

                Log.e(
                    "Firebase Database",
                    "Error getting support requests",
                    databaseError.toException()
                )
            }
        })
    }
}
