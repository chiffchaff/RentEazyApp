package com.renteazy.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import com.google.firebase.auth.*
import com.google.firebase.firestore.FirebaseFirestore
import com.renteazy.R
import com.renteazy.models.User

class RegisterActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore
    private lateinit var nameEditText: EditText
    private lateinit var emailEditText: EditText
    private lateinit var phoneEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var userTypeSpinner: Spinner
    private lateinit var registerButton: Button
    private lateinit var loginLink: TextView
    private val TAG = "RegisterActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate: Starting RegisterActivity initialization")
        setContentView(R.layout.activity_register)

        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                finish()
            }
        })

        try {
            // Initialize Firebase with custom options
            val options = FirebaseOptions.Builder()
                .setApiKey("AIzaSyCvjvePgpYki7ZNH8ot6WdbJUQ7cXs5BQk") // API key from google-services.json
                .setApplicationId("1:114114793549:android:d7ea9f2404bc7b25c7cc04") // App ID from google-services.json
                .setProjectId("renteazy-6948e") // Project ID from google-services.json
                .build()

            try {
                FirebaseApp.initializeApp(this, options, "RentEazy")
            } catch (e: IllegalStateException) {
                // App already initialized
            }
            
            auth = FirebaseAuth.getInstance(FirebaseApp.getInstance("RentEazy"))
            db = FirebaseFirestore.getInstance(FirebaseApp.getInstance("RentEazy"))
            auth.firebaseAuthSettings.setAppVerificationDisabledForTesting(true)
            Log.d(TAG, "onCreate: Firebase services initialized successfully")
        } catch (e: Exception) {
            Log.e(TAG, "onCreate: Failed to initialize Firebase services", e)
            Toast.makeText(this, "Failed to initialize app services. Please try again.", Toast.LENGTH_LONG).show()
            finish()
            return
        }

        initializeViews()
        setupClickListeners()
    }

    private fun initializeViews() {
        nameEditText = findViewById(R.id.nameEditText)
        emailEditText = findViewById(R.id.emailEditText)
        phoneEditText = findViewById(R.id.phoneEditText)
        passwordEditText = findViewById(R.id.passwordEditText)
        userTypeSpinner = findViewById(R.id.userTypeSpinner)
        registerButton = findViewById(R.id.registerButton)
        loginLink = findViewById(R.id.loginLink)
        Log.d(TAG, "initializeViews: All views initialized successfully")
    }

    private fun setupClickListeners() {
        registerButton.setOnClickListener {
            Log.d(TAG, "Register button clicked")
            val name = nameEditText.text.toString().trim()
            val email = emailEditText.text.toString().trim()
            val phone = phoneEditText.text.toString().trim()
            val password = passwordEditText.text.toString()
            val userType = userTypeSpinner.selectedItem.toString()

            Log.d(TAG, "Attempting registration for email: $email")
            if (validateInputs(name, email, phone, password)) {
                registerButton.isEnabled = false // Prevent double submission
                registerUser(name, email, phone, password, userType)
            }
        }

        loginLink.setOnClickListener {
            Log.d(TAG, "Login link clicked, finishing activity")
            finish()
        }
    }

    private fun validateInputs(name: String, email: String, phone: String, password: String): Boolean {
        if (name.isEmpty() || email.isEmpty() || phone.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
            return false
        }
        if (password.length < 6) {
            Toast.makeText(this, "Password must be at least 6 characters", Toast.LENGTH_SHORT).show()
            return false
        }
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(this, "Please enter a valid email", Toast.LENGTH_SHORT).show()
            return false
        }
        if (!android.util.Patterns.PHONE.matcher(phone).matches()) {
            Toast.makeText(this, "Please enter a valid phone number", Toast.LENGTH_SHORT).show()
            return false
        }
        Log.d(TAG, "validateInputs: All inputs validated successfully")
        return true
    }

    private fun registerUser(name: String, email: String, phone: String, password: String, userType: String) {
        Log.d(TAG, "Starting user registration process")
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "createUserWithEmail:success")
                    val user = User(
                        id = auth.currentUser?.uid ?: "",
                        name = name,
                        email = email,
                        phone = phone,
                        userType = userType
                    )
                    saveUserToFirestore(user)
                } else {
                    Log.e(TAG, "createUserWithEmail:failure", task.exception)
                    registerButton.isEnabled = true
                    val message = when (task.exception) {
                        is FirebaseAuthWeakPasswordException -> "Password is too weak"
                        is FirebaseAuthInvalidCredentialsException -> "Invalid email format"
                        is FirebaseAuthUserCollisionException -> "Email already registered"
                        else -> task.exception?.message ?: "Registration failed"
                    }
                    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun saveUserToFirestore(user: User) {
    Log.d(TAG, "Saving user data to Firestore")
    db.collection("users")
        .document(user.id)
        .set(user)
        .addOnSuccessListener {
            Log.d(TAG, "User data saved successfully")
            Toast.makeText(this, "Registration successful!", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
        .addOnFailureListener { e ->
            Log.e(TAG, "Error saving user data", e)
            registerButton.isEnabled = true
            val errorMessage = when {
                e.message?.contains("PERMISSION_DENIED") == true -> 
                    "Permission denied. Please check Firebase configuration."
                e.message?.contains("UNAVAILABLE") == true -> 
                    "Service unavailable. Please check your internet connection."
                else -> "Failed to save user data: ${e.message}"
            }
            Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show()
        }
    }
}
