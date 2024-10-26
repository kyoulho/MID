package com.kyoulho.mid.account.entity

import jakarta.persistence.*

@Entity
data class AccountTypeField(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    val id: String = "",

    @ManyToOne
    @JoinColumn(name = "account_type_id", nullable = false)
    var accountType: AccountType,

    @Column(nullable = false, length = 200)
    var fieldName: String,

    @Column(nullable = false, length = 50)
    var fieldType: String  // 예: "NUMBER", "DATE", "TEXT" 등
)
