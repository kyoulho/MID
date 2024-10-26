package com.kyoulho.mid.account.entity

import jakarta.persistence.*

@Entity
data class AccountTypeFieldValue(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    val id: String = "",

    @ManyToOne
    @JoinColumn(name = "account_id", nullable = false)
    var account: Account,

    @ManyToOne
    @JoinColumn(name = "account_type_field_id", nullable = false)
    var accountTypeField: AccountTypeField,

    @Column(nullable = false)
    var value: String?
)
