

package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.databinding.ActivitySignUpBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class SignUp : AppCompatActivity() {

    private lateinit var binding: ActivitySignUpBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var databaseReference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()
        databaseReference = FirebaseDatabase.getInstance().reference.child("users")

        binding.buttonSignUp.setOnClickListener {
            val signupName = binding.editTextName.text?.toString() ?: ""
            val signupEmail = binding.editTextEmail.text?.toString() ?: ""
            val signupPassword = binding.editTextPassword.text?.toString() ?: ""
            val confirmPassword = binding.editTextConfirmPassword.text?.toString() ?: ""

            if (signupEmail.isNotEmpty() && signupPassword.isNotEmpty() && confirmPassword.isNotEmpty()) {
                if (signupPassword == confirmPassword) {
                    signUpUser(signupName, signupEmail, signupPassword)
                } else {
                    Toast.makeText(this@SignUp, "Passwords do not match", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this@SignUp, "All fields are mandatory", Toast.LENGTH_SHORT).show()
            }
        }

        binding.loginRedirect.setOnClickListener {
            startActivity(Intent(this@SignUp, SignIn::class.java))
            finish()
        }
    }

    private fun signUpUser(name: String, email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    val userId = user?.uid
                    val role = if (email.endsWith("@trainer.com")) "trainer" else "user"

                    userId?.let {
                        val userData = User(name = name, email = email, password = password, role = role)
                        databaseReference.child(userId).setValue(userData)

                        if (role == "trainer") {
                            startActivity(Intent(this@SignUp, TrainerHome::class.java))
                        } else {
                            startActivity(Intent(this@SignUp, MainActivity3::class.java))
                        }

                        Toast.makeText(this@SignUp, "SignUp Success", Toast.LENGTH_SHORT).show()
                        finish()
                    }
                } else {
                    Toast.makeText(this@SignUp, "Authentication failed: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                }
            }
    }
}


