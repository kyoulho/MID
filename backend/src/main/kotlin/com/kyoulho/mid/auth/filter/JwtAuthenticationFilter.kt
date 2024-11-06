package com.kyoulho.mid.auth.filter

import com.kyoulho.mid.auth.config.PermitAllUrlsProperties
import com.kyoulho.mid.auth.svc.JwtTokenProvider
import jakarta.servlet.FilterChain
import jakarta.servlet.ServletException
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.util.matcher.AntPathRequestMatcher
import org.springframework.web.filter.OncePerRequestFilter
import java.io.IOException

class JwtAuthenticationFilter(
    private val tokenProvider: JwtTokenProvider,
    private val permitAllUrls: PermitAllUrlsProperties
) : OncePerRequestFilter() {

    override fun shouldNotFilter(request: HttpServletRequest): Boolean {
        return permitAllUrls.permitAll.any { pattern ->
            AntPathRequestMatcher(pattern).matches(request)
        }
    }

    @Throws(ServletException::class, IOException::class)
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        getJwtFromRequest(request)?.let { token ->
            tokenProvider.getUserIdFromJWT(token).let { id ->
                val authorities = tokenProvider.getAuthoritiesFromJWT(token)
                val authentication = UsernamePasswordAuthenticationToken(id, null, authorities)
                SecurityContextHolder.getContext().authentication = authentication
            }
        }

        filterChain.doFilter(request, response)
    }

    private fun getJwtFromRequest(request: HttpServletRequest): String? =
        request.getHeader("Authorization")?.takeIf { it.startsWith("Bearer ") }
            ?.substring(7)
}
