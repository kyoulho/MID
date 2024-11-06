package com.kyoulho.mid.auth.ctr

import com.kyoulho.mid.auth.dto.*
import com.kyoulho.mid.auth.svc.JwtTokenProvider
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/auth")
class AuthController(
    private val jwtTokenProvider: JwtTokenProvider,
) {

    private val log = LoggerFactory.getLogger(AuthController::class.java)


    @PostMapping("/login")
    fun login(@RequestBody loginRequest: LoginRequest): ResponseEntity<JwtResponse> {
        TODO("스웨거용")
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/refresh")
    fun refreshToken(@AuthenticationPrincipal principal: UserPrincipal): JwtResponse {
        log.info("토큰 재발급 요청: {}", principal.id)
        return JwtResponse(
            jwtTokenProvider.createAccessToken(principal.id, principal.authorities.map { it.authority }),
            jwtTokenProvider.createRefreshToken(principal.id)
        )
    }
}
