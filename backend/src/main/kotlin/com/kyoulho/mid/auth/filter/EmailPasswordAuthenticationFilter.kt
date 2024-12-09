package com.kyoulho.mid.auth.filter

import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter
import org.springframework.security.web.util.matcher.AntPathRequestMatcher

data class LoginRequest(
    val email: String,
    val password: String
)

class EmailPasswordAuthenticationFilter(
    authenticationManager: AuthenticationManager,
    private val objectMapper: ObjectMapper
) : AbstractAuthenticationProcessingFilter(AntPathRequestMatcher("/api/auth/login", "POST")) {

    init {
        this.authenticationManager = authenticationManager
    }

    override fun attemptAuthentication(
        request: HttpServletRequest,
        response: HttpServletResponse
    ): Authentication {
        val loginRequest = objectMapper.readValue(request.inputStream, LoginRequest::class.java)
        val authRequest = UsernamePasswordAuthenticationToken(loginRequest.email, loginRequest.password)
        return authenticationManager.authenticate(authRequest)
    }

    override fun successfulAuthentication(
        request: HttpServletRequest,
        response: HttpServletResponse,
        chain: FilterChain?,
        authResult: Authentication
    ) {
        super.successfulAuthentication(request, response, chain, authResult)
    }

    override fun unsuccessfulAuthentication(
        request: HttpServletRequest,
        response: HttpServletResponse,
        failed: AuthenticationException
    ) {
        super.unsuccessfulAuthentication(request, response, failed)
    }
}