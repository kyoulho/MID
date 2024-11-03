package com.kyoulho.mid.account.dto

import com.kyoulho.mid.const.AccountFieldDataType
import com.kyoulho.mid.const.AccountType

data class AccountTypeDTO(
    val type: String,
    val fields: List<AccountFieldDTO>
)

data class AccountFieldDTO(
    val name: String,
    val type: AccountFieldDataType
)

fun AccountType.toDTO(): AccountTypeDTO {
    return AccountTypeDTO(
        type = this.name,
        fields = this.fields
            .map { AccountFieldDTO(it.name, it.dataType) }
    )
}
