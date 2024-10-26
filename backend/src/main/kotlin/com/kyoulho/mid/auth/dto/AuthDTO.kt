package com.kyoulho.mid.auth.dto

data class LoginRequest(
    val email: String,
    val password: String
)

data class JwtResponse(
    val accessToken: String,
    val refreshToken : String
)

data class RefreshRequest(
    val refreshToken: String
)
