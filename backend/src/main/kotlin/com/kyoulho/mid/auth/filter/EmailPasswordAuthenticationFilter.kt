package com.kyoulho.mid.auth.filter

import com.fasterxml.jackson.databind.ObjectMapper
import com.kyoulho.mid.auth.dto.JwtResponse
import com.kyoulho.mid.auth.dto.LoginRequest
import com.kyoulho.mid.auth.dto.UserPrincipal
import com.kyoulho.mid.auth.svc.JwtTokenProvider
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpMethod
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.web.authentication.AuthenticationFailureHandler
import org.springframework.security.web.authentication.AuthenticationSuccessHandler
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.security.web.util.matcher.AntPathRequestMatcher

class EmailPasswordAuthenticationFilter(
    authenticationManager: AuthenticationManager,
    private val objectMapper: ObjectMapper,
    private val jwtTokenProvider: JwtTokenProvider
) : UsernamePasswordAuthenticationFilter(authenticationManager) {

    init {
        setRequiresAuthenticationRequestMatcher(
            AntPathRequestMatcher("/api/auth/login", HttpMethod.POST.name())
        )
        setAuthenticationSuccessHandler(authenticationSuccessHandler())
        setAuthenticationFailureHandler(authenticationFailureHandler())
    }

    override fun attemptAuthentication(
        request: HttpServletRequest,
        response: HttpServletResponse
    ): Authentication {
        val loginRequest = objectMapper.readValue(request.reader, LoginRequest::class.java)
        val authRequest = UsernamePasswordAuthenticationToken(loginRequest.email, loginRequest.password)
        return authenticationManager.authenticate(authRequest)
    }

    private fun authenticationSuccessHandler() = AuthenticationSuccessHandler { _, response, authentication ->
        val principal = authentication.principal as UserPrincipal
        val accessToken = jwtTokenProvider.createAccessToken(principal)
        val refreshToken = jwtTokenProvider.createRefreshToken(principal)

        response.contentType = "application/json"
        response.characterEncoding = "UTF-8"
        val jwtResponse = JwtResponse(accessToken, refreshToken)
        response.writer.write(objectMapper.writeValueAsString(jwtResponse))
    }

    private fun authenticationFailureHandler() = AuthenticationFailureHandler { _, response, exception ->
        response.status = HttpServletResponse.SC_UNAUTHORIZED
        response.contentType = "application/json"
        response.characterEncoding = "UTF-8"
        response.writer.write("{\"error\": \"${exception.message}\"}")
    }
}