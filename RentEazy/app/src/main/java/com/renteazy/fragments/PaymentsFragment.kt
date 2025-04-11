package com.renteazy.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.renteazy.R
import com.renteazy.models.Payment
import com.renteazy.models.User
import java.util.Calendar

class PaymentsFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore
    private var currentUser: User? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_payments, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()

        recyclerView = view.findViewById(R.id.paymentsRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(context)

        loadUserData()
    }

    private fun loadUserData() {
        val userId = auth.currentUser?.uid ?: return
        
        db.collection("users").document(userId)
            .get()
            .addOnSuccessListener { document ->
                currentUser = document.toObject(User::class.java)
                loadPayments()
            }
    }

    private fun loadPayments() {
        currentUser?.let { user ->
            val query = when (user.userType) {
                "OWNER" -> db.collection("payments")
                    .whereEqualTo("ownerId", user.id)
                else -> db.collection("payments")
                    .whereEqualTo("tenantId", user.id)
            }

            query.orderBy("createdAt", Query.Direction.DESCENDING)
                .addSnapshotListener { snapshot, e ->
                    if (e != null) return@addSnapshotListener

                    val payments = snapshot?.documents?.mapNotNull {
                        it.toObject(Payment::class.java)
                    } ?: listOf()

                    // TODO: Update RecyclerView adapter with payments
                    updatePaymentsList(payments)
                }
        }
    }

    private fun updatePaymentsList(payments: List<Payment>) {
        val calendar = Calendar.getInstance()
        val currentMonth = calendar.get(Calendar.MONTH) + 1
        val currentYear = calendar.get(Calendar.YEAR)

        val pendingPayments = payments.filter { 
            it.status == "PENDING" && 
            (it.month == currentMonth && it.year == currentYear)
        }
        val completedPayments = payments.filter { it.status == "COMPLETED" }

        // TODO: Update UI with pending and completed payments
    }
}