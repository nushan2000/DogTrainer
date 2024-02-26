package com.example.myapplication.ui.dashboard

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.Messages
import com.example.myapplication.R
import com.example.myapplication.SupportRequest
import com.example.myapplication.SupportRequestAdapter
import com.example.myapplication.databinding.FragmentDashboardBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class DashboardFragment : Fragment() {
    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!

    private lateinit var supportRequestList: MutableList<SupportRequest>
    private lateinit var recyclerView: RecyclerView
    private lateinit var supportRequestAdapter: SupportRequestAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        val root: View = binding.root


        recyclerView = root.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())


        supportRequestList = mutableListOf()
        supportRequestAdapter = SupportRequestAdapter(supportRequestList)
        recyclerView.adapter = supportRequestAdapter


        val userId = FirebaseAuth.getInstance().currentUser?.uid

        if (userId != null) {

            val databaseReference = FirebaseDatabase.getInstance().getReference("supportRequests/$userId")


            databaseReference.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {

                    supportRequestList.clear()


                    for (childSnapshot in dataSnapshot.children) {
                        val request = childSnapshot.getValue(SupportRequest::class.java)
                        request?.let {
                            supportRequestList.add(it)
                        }
                    }


                }

                override fun onCancelled(databaseError: DatabaseError) {

                }
            })

            val button5 = root.findViewById<Button>(R.id.buttonme)
            button5.setOnClickListener {
                val intent = Intent(requireContext(), Messages::class.java)
                startActivity(intent)
            }
        } else {

            println("User not authenticated")
        }


        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
