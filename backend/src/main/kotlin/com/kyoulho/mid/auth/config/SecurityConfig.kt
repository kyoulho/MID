package com.kyoulho.mid.auth.config

import com.fasterxml.jackson.databind.ObjectMapper
import com.kyoulho.mid.auth.filter.EmailPasswordAuthenticationFilter
import com.kyoulho.mid.auth.filter.JwtAuthenticationFilter
import com.kyoulho.mid.auth.svc.CustomUserDetailsService
import com.kyoulho.mid.auth.svc.JwtTokenProvider
import jakarta.servlet.http.HttpServletResponse
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.dao.DaoAuthenticationProvider
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
class SecurityConfig(
    private val userDetailsService: CustomUserDetailsService,
    private val permitAllUrls: PermitAllUrlsProperties,
    private val jwtTokenProvider: JwtTokenProvider,
) {

    @Bean
    fun jwtAuthenticationFilter(): JwtAuthenticationFilter {
        return JwtAuthenticationFilter(jwtTokenProvider, permitAllUrls)
    }

    @Bean
    fun emailPasswordAuthenticationFilter(
        authenticationManager: AuthenticationManager
    ): EmailPasswordAuthenticationFilter {
        return EmailPasswordAuthenticationFilter(
            authenticationManager,
            ObjectMapper(),
            jwtTokenProvider
        )
    }

    @Bean
    fun securityFilterChain(
        http: HttpSecurity,
        authenticationManager: AuthenticationManager
    ): SecurityFilterChain {
        http
            .csrf { it.disable() }
            .sessionManagement { it.sessionCreationPolicy(SessionCreationPolicy.STATELESS) }
            .authorizeHttpRequests { authz ->
                permitAllUrls.permitAll.forEach { pattern ->
                    authz.requestMatchers(pattern).permitAll()
                }
                authz.anyRequest().authenticated()
            }
            .authenticationProvider(daoAuthenticationProvider())
            .addFilterAt(
                emailPasswordAuthenticationFilter(authenticationManager),
                UsernamePasswordAuthenticationFilter::class.java
            )
            .addFilterAfter(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter::class.java)
            .exceptionHandling {
                it.authenticationEntryPoint { _, response, authException ->
                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED, authException.message)
                }
            }

        return http.build()
    }

    @Bean
    fun daoAuthenticationProvider(): DaoAuthenticationProvider {
        val provider = DaoAuthenticationProvider()
        provider.setUserDetailsService(userDetailsService)
        provider.setPasswordEncoder(passwordEncoder())
        return provider
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder = BCryptPasswordEncoder()

    @Bean
    fun authenticationManager(authenticationConfiguration: AuthenticationConfiguration): AuthenticationManager {
        return authenticationConfiguration.authenticationManager
    }
}