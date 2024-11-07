package com.kyoulho.mid.auth.config

import com.fasterxml.jackson.databind.ObjectMapper
import com.kyoulho.mid.auth.filter.EmailPasswordAuthenticationFilter
import com.kyoulho.mid.auth.svc.CustomUserDetailsService
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
    private val properties: SecurityProperties,
) {

    @Bean
    fun emailPasswordAuthenticationFilter(
        authenticationManager: AuthenticationManager
    ): EmailPasswordAuthenticationFilter {
        return EmailPasswordAuthenticationFilter(
            authenticationManager, ObjectMapper(),
        ).apply {
            // 로그인 성공 시 리다이렉트나 추가 로직을 여기에 설정할 수 있습니다.
            setAuthenticationSuccessHandler { request, response, authentication ->
                // 성공 처리 로직
                response.status = HttpServletResponse.SC_OK
                // 필요 시 사용자 정보를 응답으로 반환
            }

            // 로그인 실패 시 처리
            setAuthenticationFailureHandler { request, response, exception ->
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, exception.message)
            }
        }
    }

    @Bean
    fun securityFilterChain(
        http: HttpSecurity, authenticationManager: AuthenticationManager
    ): SecurityFilterChain {
        http
            .csrf { it.disable() }
            .sessionManagement {
                it.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
            }
            .authorizeHttpRequests { authz ->
                properties.permitAll.forEach { pattern ->
                    authz.requestMatchers(pattern).permitAll()
                }
                authz.anyRequest().authenticated()
            }
            .authenticationProvider(daoAuthenticationProvider())
            .addFilterAt(
                emailPasswordAuthenticationFilter(authenticationManager),
                UsernamePasswordAuthenticationFilter::class.java
            )
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