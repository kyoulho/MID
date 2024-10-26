package com.kyoulho.mid.account.entity

import com.kyoulho.mid.account.enum.AccountFieldEnum
import com.kyoulho.mid.account.enum.AccountTypeEnum
import com.kyoulho.mid.user.entity.MidUser
import io.hypersistence.utils.hibernate.type.json.JsonBinaryType
import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.JdbcTypeCode
import org.hibernate.annotations.Type
import org.hibernate.type.SqlTypes
import java.math.BigDecimal
import java.sql.SQLType
import java.time.LocalDateTime

@Entity
data class Account(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    val id: String? = null,

    @Column(length = 200, nullable = false)
    var name: String,

    @Column(length = 200, nullable = false)
    var description: String?,

    @Column(length = 200, nullable = false)
    var issuer: String,

    @Column(length = 200, nullable = false)
    var number: String,

    @Column(nullable = false)
    var interestRate: BigDecimal,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 50)
    var accountType: AccountTypeEnum,

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "jsonb")
    val fields: MutableMap<AccountFieldEnum, String> = mutableMapOf(),

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id")
    val user: MidUser,

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    val createdAt: LocalDateTime = LocalDateTime.now()
)
