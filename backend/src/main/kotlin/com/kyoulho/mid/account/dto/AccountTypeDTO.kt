package com.kyoulho.mid.account.dto

import com.kyoulho.mid.account.enum.AccountTypeEnum

data class AccountTypeDTO(
    val type: String,
    val fields: List<AccountFieldDTO>
)

data class AccountFieldDTO(
    val name: String,
    val type: String
)

fun AccountTypeEnum.toDTO(): AccountTypeDTO {
    return AccountTypeDTO(
        type = this.name,
        fields = this.fields
            .map { AccountFieldDTO(it.name, it.fieldType.name) }
    )
}
