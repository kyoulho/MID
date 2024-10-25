package com.kyoulho.mid.user.repo

import com.kyoulho.mid.user.entity.MidUser
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository : JpaRepository<MidUser, String> {
    fun existsByEmail(email: String): Boolean
    fun findByEmail(email: String): MidUser?
}