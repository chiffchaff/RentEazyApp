package com.renteazy.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.renteazy.R
import com.renteazy.models.Property
import com.renteazy.models.User

class DashboardFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var addPropertyFab: FloatingActionButton
    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore
    private var currentUser: User? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_dashboard, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()

        recyclerView = view.findViewById(R.id.propertiesRecyclerView)
        addPropertyFab = view.findViewById(R.id.addPropertyFab)
        
        recyclerView.layoutManager = LinearLayoutManager(context)

        loadUserData()
    }

    private fun loadUserData() {
        val userId = auth.currentUser?.uid ?: return
        
        db.collection("users").document(userId)
            .get()
            .addOnSuccessListener { document ->
                currentUser = document.toObject(User::class.java)
                setupDashboard()
            }
    }

    private fun setupDashboard() {
        when (currentUser?.userType) {
            "OWNER" -> setupOwnerDashboard()
            "TENANT" -> setupTenantDashboard()
        }
    }

    private fun setupOwnerDashboard() {
        addPropertyFab.visibility = View.VISIBLE
        addPropertyFab.setOnClickListener {
            // Navigate to add property screen
            // TODO: Implement navigation
        }

        loadOwnerProperties()
    }

    private fun setupTenantDashboard() {
        addPropertyFab.visibility = View.GONE
        loadRentedProperties()
    }

    private fun loadOwnerProperties() {
        currentUser?.let { user ->
            db.collection("properties")
                .whereEqualTo("ownerId", user.id)
                .addSnapshotListener { snapshot, e ->
                    if (e != null) return@addSnapshotListener
                    
                    val properties = snapshot?.documents?.mapNotNull {
                        it.toObject(Property::class.java)
                    } ?: listOf()
                    
                    // TODO: Update RecyclerView adapter with properties
                }
        }
    }

    private fun loadRentedProperties() {
        currentUser?.let { user ->
            db.collection("properties")
                .whereEqualTo("currentTenantId", user.id)
                .addSnapshotListener { snapshot, e ->
                    if (e != null) return@addSnapshotListener
                    
                    val properties = snapshot?.documents?.mapNotNull {
                        it.toObject(Property::class.java)
                    } ?: listOf()
                    
                    // TODO: Update RecyclerView adapter with properties
                }
        }
    }
}