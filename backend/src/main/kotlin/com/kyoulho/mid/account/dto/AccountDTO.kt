package com.kyoulho.mid.account.dto

import com.kyoulho.mid.account.entity.Account
import com.kyoulho.mid.const.AccountField
import com.kyoulho.mid.const.AccountType
import java.math.BigDecimal
import java.time.LocalDateTime


data class CreateAccountDTO(
    val name: String,
    val description: String?,
    val issuer: String,
    val number: String,
    val interestRate: BigDecimal,
    val accountType: AccountType,
    val fields: Map<AccountField, String>,
    val createdAt: LocalDateTime
)

typealias UpdateAccountDTO = CreateAccountDTO


data class GetAccountDTO(
    val id: String,
    val name: String,
    val description: String?,
    val issuer: String,
    val number: String,
    val interestRate: BigDecimal,
    val accountType: AccountType,
    val fields: Map<AccountField, String>,
    val createdAt: LocalDateTime
)

fun Account.toDTO(): GetAccountDTO {
    return GetAccountDTO(
        id = this.id,
        accountType = this.accountType,
        name = this.name,
        description = this.description,
        issuer = this.issuer,
        number = this.number,
        interestRate = this.interestRate,
        createdAt = this.createdAt,
        fields = this.fields,
    )
}

