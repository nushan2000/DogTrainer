package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.databinding.ActivitySigninBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class SignIn : AppCompatActivity() {

    private lateinit var binding: ActivitySigninBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var databaseReference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySigninBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()
        databaseReference = FirebaseDatabase.getInstance().reference.child("users")

        binding.buttonSignIn.setOnClickListener {
            val signinEmail = binding.editTextTextEmailAddress.text?.toString() ?: ""
            val signinPassword = binding.editTextTextPassword.text?.toString() ?: ""

            if (signinEmail.isNotEmpty() && signinPassword.isNotEmpty()) {
                signIn(signinEmail, signinPassword)
            } else {
                Toast.makeText(this@SignIn, "All fields are mandatory", Toast.LENGTH_SHORT).show()
            }
        }

        binding.signupRE.setOnClickListener {
            startActivity(Intent(this@SignIn, SignUp::class.java))
            finish()
        }
    }

    private fun signIn(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    val userId = user?.uid

                    userId?.let {

                        databaseReference.child(userId).addListenerForSingleValueEvent(object :
                            ValueEventListener {
                            override fun onDataChange(dataSnapshot: DataSnapshot) {
                                val userRole = dataSnapshot.child("role").getValue(String::class.java)


                                if (userRole == "trainer") {
                                    startActivity(Intent(this@SignIn, TrainerHome::class.java))
                                } else {
                                    startActivity(Intent(this@SignIn, MainActivity3::class.java))
                                }
                                finish()
                            }

                            override fun onCancelled(databaseError: DatabaseError) {
                                Toast.makeText(this@SignIn, "Database Error: ${databaseError.message}", Toast.LENGTH_SHORT).show()
                            }
                        })
                    }
                } else {

                    Toast.makeText(this@SignIn, "Authentication failed.", Toast.LENGTH_SHORT).show()
                }
            }
    }
}
