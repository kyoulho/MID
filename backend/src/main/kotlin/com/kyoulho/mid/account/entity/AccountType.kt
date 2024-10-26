package com.kyoulho.mid.account.entity

import jakarta.persistence.*

@Entity
data class AccountType(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    val id: String = "",

    @Column(nullable = false, unique = true, length = 200)
    var name: String,

    @Column(length = 500)
    var description: String? = null,

    @OneToMany(mappedBy = "accountType", cascade = [CascadeType.ALL], orphanRemoval = true)
    val fields: MutableList<AccountTypeField> = mutableListOf()
) {
}
