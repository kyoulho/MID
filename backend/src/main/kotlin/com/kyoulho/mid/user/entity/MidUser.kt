package com.kyoulho.mid.user.entity

import com.kyoulho.mid.account.entity.Account
import com.kyoulho.mid.portfolio.entity.Portfolio
import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import java.time.LocalDateTime

@Entity

data class MidUser(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    val id: String = "",

    @Column(length = 50, nullable = false)
    val email: String,

    @Column(length = 60, nullable = false)
    val hashedPassword: String,

    @Column(length = 10, nullable = false)
    val name: String,

    @Column(length = 11, nullable = false)
    val phoneNumber: String,

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    val createdAt: LocalDateTime? = null,

    @OneToMany(mappedBy = "user", cascade = [CascadeType.ALL], orphanRemoval = true, fetch = FetchType.LAZY)
    val accounts: List<Account> = mutableListOf(),

    @OneToMany(mappedBy = "user", cascade = [CascadeType.ALL], orphanRemoval = true, fetch = FetchType.LAZY)
    val portfolios: List<Portfolio> = mutableListOf(),

    @Column(length = 10, nullable = false)
    val role: String,

    @Column(name = "last_password_changed_at")
    val lastPasswordChangedAt: LocalDateTime? = null
)
