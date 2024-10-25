package com.kyoulho.mid.auth.ctr

import com.kyoulho.mid.auth.svc.JwtTokenProvider
import com.kyoulho.mid.auth.dto.JwtResponse
import com.kyoulho.mid.auth.dto.LoginRequest
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/auth")
class AuthController(
    private val authenticationManager: AuthenticationManager,
    private val jwtTokenProvider: JwtTokenProvider
) {

    @PostMapping("/login")
    fun authenticateUser(@RequestBody loginRequest: LoginRequest): JwtResponse {
        val authentication: Authentication = authenticationManager.authenticate(
            UsernamePasswordAuthenticationToken(
                loginRequest.email,
                loginRequest.password
            )
        )

        val jwt = jwtTokenProvider.generateToken(authentication)
        val roles = authentication.authorities.map { it.authority }

        return JwtResponse(jwt, authentication.name, roles)
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/admin")
    fun adminEndpoint(): ResponseEntity<String> {
        return ResponseEntity.ok("Admin Content")
    }
}
