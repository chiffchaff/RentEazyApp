package com.renteazy.models

data class Payment(
    val id: String = "",
    val propertyId: String = "",
    val tenantId: String = "",
    val ownerId: String = "",
    val amount: Double = 0.0,
    val type: String = "RENT", // RENT, SECURITY_DEPOSIT, UTILITY
    val status: String = "PENDING", // PENDING, COMPLETED, FAILED
    val dueDate: Long = 0,
    val paidDate: Long? = null,
    val month: Int = 0, // 1-12
    val year: Int = 0,
    val utilityDetails: Map<String, Double> = mapOf(), // e.g., "electricity" -> 1000.0
    val paymentMethod: String = "", // UPI, BANK_TRANSFER, etc.
    val transactionId: String = "",
    val notes: String = "",
    val createdAt: Long = System.currentTimeMillis()
)