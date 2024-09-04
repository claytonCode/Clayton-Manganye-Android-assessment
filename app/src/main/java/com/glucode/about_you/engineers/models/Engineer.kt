package com.glucode.about_you.engineers.models

data class Engineer(
    val name: String,
    val role: String,
    var profileImageUri: String? = null,  // Add this field to store the image URI
    val defaultImageName: String,
    val quickStats: QuickStats,
    val questions: List<Question>,
)