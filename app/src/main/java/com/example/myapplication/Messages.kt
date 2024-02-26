package com.example.myapplication

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class Messages : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var chatAdapter: ChatAdapter
    private lateinit var messagesReference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_message)

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)


        val userId = FirebaseAuth.getInstance().currentUser?.uid
        messagesReference = FirebaseDatabase.getInstance().getReference("messages/$userId")


        messagesReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val messages = mutableListOf<Message>()
                for (messageSnapshot in dataSnapshot.children) {
                    val message = messageSnapshot.getValue(Message::class.java)
                    message?.let {
                        messages.add(it)
                    }
                }
                updateChatAdapter(messages)
            }

            override fun onCancelled(databaseError: DatabaseError) {

            }
        })
    }

    private fun updateChatAdapter(messages: List<Message>) {
        if (!::chatAdapter.isInitialized) {
            chatAdapter = ChatAdapter(messages)
            recyclerView.adapter = chatAdapter
        } else {
            chatAdapter.notifyDataSetChanged()
        }
    }


    private fun sendMessage(content: String) {
        val newMessage = Message(senderId = FirebaseAuth.getInstance().currentUser?.uid ?: "", content = content)
        messagesReference.push().setValue(newMessage)
    }
}