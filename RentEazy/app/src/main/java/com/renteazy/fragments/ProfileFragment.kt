package com.renteazy.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.renteazy.R
import com.renteazy.activities.LoginActivity
import com.renteazy.models.User

class ProfileFragment : Fragment() {
    private lateinit var nameTextView: TextView
    private lateinit var emailTextView: TextView
    private lateinit var phoneTextView: TextView
    private lateinit var userTypeTextView: TextView
    private lateinit var editProfileButton: Button
    private lateinit var logoutButton: Button
    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()

        nameTextView = view.findViewById(R.id.nameTextView)
        emailTextView = view.findViewById(R.id.emailTextView)
        phoneTextView = view.findViewById(R.id.phoneTextView)
        userTypeTextView = view.findViewById(R.id.userTypeTextView)
        editProfileButton = view.findViewById(R.id.editProfileButton)
        logoutButton = view.findViewById(R.id.logoutButton)

        loadUserProfile()
        setupButtons()
    }

    private fun loadUserProfile() {
        val userId = auth.currentUser?.uid ?: return

        db.collection("users").document(userId)
            .get()
            .addOnSuccessListener { document ->
                val user = document.toObject(User::class.java)
                user?.let { updateUI(it) }
            }
    }

    private fun updateUI(user: User) {
        nameTextView.text = user.name
        emailTextView.text = user.email
        phoneTextView.text = user.phone
        userTypeTextView.text = user.userType
    }

    private fun setupButtons() {
        editProfileButton.setOnClickListener {
            // TODO: Navigate to edit profile screen
        }

        logoutButton.setOnClickListener {
            auth.signOut()
            activity?.let {
                startActivity(Intent(it, LoginActivity::class.java))
                it.finish()
            }
        }
    }
}