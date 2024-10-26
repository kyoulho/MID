package com.kyoulho.mid.account.dto

import com.kyoulho.mid.account.entity.AccountType

data class GetAccountTypeDTO(
    val id: String,
    val name: String,
    val description: String?,
    val fields: List<AccountTypeFieldDTO>
)

data class CreateAccountTypeDTO(
    val name: String,
    val description: String?,
    val fields: List<AccountTypeFieldDTO>
)

data class UpdateAccountTypeDTO(
    val name: String,
    val description: String?,
    val fields: List<AccountTypeFieldDTO>
)


// AccountType 엔티티를 DTO로 변환하는 확장 함수
fun AccountType.toDTO(): GetAccountTypeDTO {
    return GetAccountTypeDTO(
        id = this.id,
        name = this.name,
        description = this.description,
        fields = this.fields.map { it.toDTO() }
    )
}
