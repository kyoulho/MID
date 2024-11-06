package com.kyoulho.mid.auth.dto

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty

data class LoginRequest @JsonCreator constructor(
    @JsonProperty("email") val email: String,
    @JsonProperty("password") val password: String
)

data class JwtResponse(
    val accessToken: String,
    val refreshToken: String
)

data class RefreshRequest(
    val refreshToken: String
)
