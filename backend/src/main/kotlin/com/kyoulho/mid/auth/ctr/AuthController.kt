package com.kyoulho.mid.auth.ctr

import com.kyoulho.mid.auth.annotation.RequestUserId
import com.kyoulho.mid.auth.dto.*
import com.kyoulho.mid.auth.svc.JwtTokenProvider
import org.slf4j.LoggerFactory
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/auth")
class AuthController(
    private val jwtTokenProvider: JwtTokenProvider,
    private val authenticationManager: AuthenticationManager
) {

    private val log = LoggerFactory.getLogger(AuthController::class.java)

    @PostMapping("/login")
    fun login(@RequestBody loginRequest: LoginRequest): JwtResponse {
        log.info("로그인 요청: {}", loginRequest.email)
        val authentication = authenticationManager.authenticate(
            UsernamePasswordAuthenticationToken(
                loginRequest.email,
                loginRequest.password
            )
        )
        return JwtResponse(
            jwtTokenProvider.createAccessToken(authentication.principal as UserPrincipal),
            jwtTokenProvider.createRefreshToken(authentication.principal as UserPrincipal),
        )
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/refresh")
    fun refreshToken(@RequestUserId id: String): JwtResponse {
        log.info("토큰 재발급 요청: {}", id)
        return JwtResponse(
            jwtTokenProvider.createAccessTokenFromUserId(id),
            jwtTokenProvider.createRefreshTokenFromUserId(id),
        )
    }
}
