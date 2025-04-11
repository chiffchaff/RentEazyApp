package com.renteazy.models

import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.PropertyName

data class User(
    @DocumentId
    val id: String = "",
    
    @PropertyName("name")
    val name: String = "",
    
    @PropertyName("email")
    val email: String = "",
    
    @PropertyName("phone")
    val phone: String = "",
    
    @PropertyName("userType")
    val userType: String = "", // "OWNER" or "TENANT"
    
    @PropertyName("properties")
    val properties: List<String> = listOf(), // List of property IDs
    
    @PropertyName("rentals")
    val rentals: List<String> = listOf()     // List of rental IDs
)