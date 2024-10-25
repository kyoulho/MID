package com.kyoulho.mid.auth.dto

data class LoginRequest(
    val email: String,
    val password: String
)

data class JwtResponse(
    val token: String,
    val username: String,
    val roles: List<String>,
)
