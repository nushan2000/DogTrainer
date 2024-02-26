package com.example.myapplication

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.databinding.ActivityAllSupportRequestsBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class AllSupportRequestActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var acceptRequestAdapter: AcceptRequestAdapter
    private lateinit var user: MutableList<User>
    private lateinit var acceptRequests: MutableList<SupportRequest>
    private lateinit var binding: ActivityAllSupportRequestsBinding


    private val databaseReference: DatabaseReference =
        FirebaseDatabase.getInstance().getReference("supportRequests")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_all_support_requests)
        acceptRequests = mutableListOf()

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)



        acceptRequestAdapter = AcceptRequestAdapter(acceptRequests, databaseReference) {

            acceptRequestAdapter.notifyDataSetChanged()
        }
        recyclerView.adapter = acceptRequestAdapter


        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                Log.d("Firebase Database", "onDataChange triggered")

                acceptRequests.clear()


                for (userSnapshot in dataSnapshot.children) {
                    for (childSnapshot in userSnapshot.children) {
                        val request = childSnapshot.getValue(SupportRequest::class.java)
                        if (request?.status == "notaccept") {
                            acceptRequests.add(request)
                        }
                    }
                }


                acceptRequestAdapter.notifyDataSetChanged()
            }

            override fun onCancelled(databaseError: DatabaseError) {

                Log.e(
                    "Firebase Database",
                    "Error getting support requests",
                    databaseError.toException()
                )
            }
        })

        //////////////////////////////////////////////



        }



    ///////////////////////////////////////////////////


}
