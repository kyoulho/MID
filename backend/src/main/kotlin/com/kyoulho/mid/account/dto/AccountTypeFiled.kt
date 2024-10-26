package com.kyoulho.mid.account.dto

import com.kyoulho.mid.account.entity.AccountTypeField

data class AccountTypeFieldDTO(
    val fieldName: String,
    val fieldType: String
)

fun AccountTypeField.toDTO(): AccountTypeFieldDTO {
    return AccountTypeFieldDTO(
        fieldName = this.fieldName,
        fieldType = this.fieldType
    )
}
