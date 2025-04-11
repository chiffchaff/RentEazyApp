package com.renteazy.models

data class Property(
    val id: String = "",
    val ownerId: String = "",
    val title: String = "",
    val description: String = "",
    val address: String = "",
    val city: String = "",
    val state: String = "",
    val zipCode: String = "",
    val propertyType: String = "",
    val bedrooms: Int = 0,
    val bathrooms: Int = 0,
    val squareFootage: Int = 0,
    val rentAmount: Double = 0.0,
    val securityDeposit: Double = 0.0,
    val isAvailable: Boolean = true,
    val images: List<String> = listOf()
)