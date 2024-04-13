package com.study.government.model

data class User(
    val id: String,
    val name: String,
    val email: String,
    val phone: String,
    val snils: String,
    val password: String,
    val avatar: String,
    val role: UserRole,
)