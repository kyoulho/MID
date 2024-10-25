package com.kyoulho.mid.auth.svc

import com.kyoulho.mid.auth.dto.UserPrincipal
import com.kyoulho.mid.exception.MIDException
import com.kyoulho.mid.logger.logger
import io.jsonwebtoken.*
import io.jsonwebtoken.security.Keys
import jakarta.annotation.PostConstruct
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpStatus
import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.stereotype.Component
import java.util.*
import java.util.stream.Collectors
import javax.crypto.SecretKey

@Component
class JwtTokenProvider(
    @Value("\${jwt.secret}") private val jwtSecret: String,
    @Value("\${jwt.expirationHour}") private val expirationHour: Long
) {
    val log = logger()

    private lateinit var secretKey: SecretKey

    @PostConstruct
    fun init() {
        val decodedKey = Base64.getDecoder().decode(jwtSecret)
        secretKey = Keys.hmacShaKeyFor(decodedKey)
    }

    fun generateToken(authentication: Authentication): String {
        val userPrincipal = authentication.principal as UserPrincipal

        val roles = userPrincipal.authorities
            .map { it.authority }

        return Jwts.builder()
            .setSubject(userPrincipal.username)
            .claim("roles", roles)
            .setIssuedAt(Date())
            .setExpiration(Date(System.currentTimeMillis() + expirationHour * 3_600_000))
            .signWith(secretKey, SignatureAlgorithm.HS512)
            .compact()
    }

    fun getEmailFromJWT(token: String): String {
        return Jwts.parserBuilder()
            .setSigningKey(secretKey)
            .build()
            .parseClaimsJws(token)
            .body
            .subject
    }

    fun getAuthoritiesFromJWT(token: String): List<GrantedAuthority> {
        val claims = Jwts.parserBuilder()
            .setSigningKey(secretKey)
            .build()
            .parseClaimsJws(token)
            .body

        val roles = claims["roles"] as List<*>
        return roles.map { role -> SimpleGrantedAuthority("ROLE_$role") }
    }

    fun validateToken(authToken: String): Boolean {
        try {
            Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(authToken)
            return true
        } catch (ex: SecurityException) {
            throw MIDException(HttpStatus.UNAUTHORIZED, "Invalid JWT signature")
        } catch (ex: MalformedJwtException) {
            throw MIDException(HttpStatus.UNAUTHORIZED, "Invalid JWT token")
        } catch (ex: ExpiredJwtException) {
            throw MIDException(HttpStatus.UNAUTHORIZED, "Expired JWT token")
        } catch (ex: UnsupportedJwtException) {
            throw MIDException(HttpStatus.UNAUTHORIZED, "Unsupported JWT token")
        } catch (ex: IllegalArgumentException) {
            throw MIDException(HttpStatus.BAD_REQUEST, "JWT claims string is empty.")
        }
    }
}
