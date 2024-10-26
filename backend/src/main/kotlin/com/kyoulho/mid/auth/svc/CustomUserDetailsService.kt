package com.kyoulho.mid.auth.svc

import com.kyoulho.mid.auth.dto.UserPrincipal
import com.kyoulho.mid.exception.MIDException
import com.kyoulho.mid.user.repo.UserRepository
import org.springframework.http.HttpStatus
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

@Service
class CustomUserDetailsService(
    private val userRepository: UserRepository
) : UserDetailsService {

    override fun loadUserByUsername(email: String): UserDetails {
        val user = userRepository.findByEmail(email)
            ?: throw MIDException(HttpStatus.UNAUTHORIZED, "잘못된 이메일입니다. email: $email")

        return UserPrincipal.create(user)
    }
}
