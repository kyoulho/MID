package com.kyoulho.mid.auth.svc

import com.kyoulho.mid.logger.logger
import com.kyoulho.mid.user.entity.MidUser
import com.kyoulho.mid.user.repo.UserRepository
import org.springframework.boot.CommandLineRunner
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component
import java.time.LocalDateTime

@Component
class InitUserRunner(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder // PasswordEncoder 주입
) : CommandLineRunner {
    val log = logger()

    override fun run(vararg args: String?) {
        val email = "kyoulho@gmail.com"

        // 동일한 이메일을 가진 유저가 이미 존재하는지 확인
        if (!userRepository.existsByEmail(email)) {
            // 유저 생성
            val user = MidUser(
                email = email,
                hashedPassword = passwordEncoder.encode("password"),
                name = "Kyoulho",
                phoneNumber = "01012345678",
                role = "USER",
                createdAt = LocalDateTime.now()
            )

            // 유저 저장
            userRepository.save(user)
            log.info("유저가 성공적으로 생성되었습니다: $email")
        } else {
            log.info("이미 존재하는 유저입니다: $email")
        }
    }
}
