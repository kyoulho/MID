package com.kyoulho.mid.auth.svc

import com.kyoulho.mid.auth.dto.UserPrincipal
import com.kyoulho.mid.exception.MIDException
import io.jsonwebtoken.JwtException
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys
import jakarta.annotation.PostConstruct
import org.springframework.http.HttpStatus
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import java.util.*
import javax.crypto.SecretKey

class JwtTokenProvider(
    private val secret: String,
    private val expirationHours: Long
) {
    private lateinit var key: SecretKey

    @PostConstruct
    fun init() {
        key = Keys.hmacShaKeyFor(Base64.getDecoder().decode(secret))
    }

    fun createAccessToken(userPrincipal: UserPrincipal): String {
        val roles = userPrincipal.authorities.map { it.authority }

        return Jwts.builder()
            .setSubject(userPrincipal.id)
            .claim("roles", roles)
            .setIssuedAt(Date())
            .setExpiration(Date(System.currentTimeMillis() + expirationHours * 3_600_000))
            .signWith(key, SignatureAlgorithm.HS512)
            .compact()
    }

    fun createRefreshToken(userPrincipal: UserPrincipal): String {
        return Jwts.builder()
            .setSubject(userPrincipal.id)
            .setIssuedAt(Date())
            .setExpiration(Date(System.currentTimeMillis() + expirationHours * 6 * 3_600_000))
            .signWith(key, SignatureAlgorithm.HS512)
            .compact()
    }

    fun createRefreshTokenFromUserId(id: String): String {
        return Jwts.builder()
            .setSubject(id)
            .setIssuedAt(Date())
            .setExpiration(Date(System.currentTimeMillis() + expirationHours * 6 * 3_600_000))
            .signWith(key, SignatureAlgorithm.HS512)
            .compact()
    }

    fun createAccessTokenFromUserId(id: String): String {
        return Jwts.builder()
            .setSubject(id)
            .setIssuedAt(Date())
            .setExpiration(Date(System.currentTimeMillis() + expirationHours * 3_600_000))
            .signWith(key, SignatureAlgorithm.HS512)
            .compact()
    }

    fun getUserIdFromJWT(token: String): String {
        return try {
            Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .body
                .subject
        } catch (e: JwtException) {
            throw MIDException(HttpStatus.UNAUTHORIZED, "Failed to extract email from JWT", e)
        }
    }

    fun getAuthoritiesFromJWT(token: String): List<GrantedAuthority> {
        val claims = Jwts.parserBuilder()
            .setSigningKey(key)
            .build()
            .parseClaimsJws(token)
            .body

        val roles = claims["roles"] as List<*>
        return roles.map { role -> SimpleGrantedAuthority("ROLE_$role") }
    }


}
