package com.renteazy.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.renteazy.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.FirebaseApp
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.NavController

class MainActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private val TAG = "MainActivity"
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate: Starting MainActivity initialization")
        setContentView(R.layout.activity_main)

        try {
            FirebaseApp.initializeApp(this)
            auth = FirebaseAuth.getInstance()
            Log.d(TAG, "onCreate: Firebase Auth initialized successfully")
        } catch (e: Exception) {
            Log.e(TAG, "onCreate: Failed to initialize Firebase Auth", e)
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
            return
        }

        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

        val navView: BottomNavigationView = findViewById(R.id.nav_view)
        
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_dashboard,
                R.id.navigation_payments,
                R.id.navigation_profile
            )
        )

        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

    override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        Log.d(TAG, "onStart: Current user: ${currentUser?.email ?: "null"}")
        if (currentUser == null) {
            Log.d(TAG, "onStart: No user found, redirecting to LoginActivity")
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }
}