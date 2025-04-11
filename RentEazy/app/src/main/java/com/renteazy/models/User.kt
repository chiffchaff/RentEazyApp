package com.renteazy.models

data class User(
    val id: String = "",
    val name: String = "",
    val email: String = "",
    val phone: String = "",
    val userType: String = "", // "OWNER" or "TENANT"
    val properties: List<String> = listOf(), // List of property IDs
    val rentals: List<String> = listOf()     // List of rental IDs
)