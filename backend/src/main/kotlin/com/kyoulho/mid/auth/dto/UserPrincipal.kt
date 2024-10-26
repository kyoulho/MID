package com.kyoulho.mid.auth.dto

import com.kyoulho.mid.user.entity.MidUser
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

class UserPrincipal(
    val id: String,
    private val email: String,
    private val hashedPassword: String,
    private val authorities: Collection<GrantedAuthority>
) : UserDetails {

    override fun getAuthorities(): Collection<GrantedAuthority> = authorities

    override fun getPassword(): String = hashedPassword

    override fun getUsername(): String = email

    override fun isAccountNonExpired(): Boolean = true

    override fun isAccountNonLocked(): Boolean = true

    override fun isCredentialsNonExpired(): Boolean = true

    override fun isEnabled(): Boolean = true

    companion object {
        fun create(user: MidUser): UserPrincipal {
            val authorities = listOf(SimpleGrantedAuthority(user.role))
            return UserPrincipal(
                user.id,
                user.email,
                user.hashedPassword,
                authorities
            )
        }
    }
}
