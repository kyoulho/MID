package com.kyoulho.mid.account.svc

import com.kyoulho.mid.account.dto.CreateAccountTypeDTO
import com.kyoulho.mid.account.dto.GetAccountTypeDTO
import com.kyoulho.mid.account.dto.UpdateAccountTypeDTO
import com.kyoulho.mid.account.dto.toDTO
import com.kyoulho.mid.account.entity.AccountType
import com.kyoulho.mid.account.entity.AccountTypeField
import com.kyoulho.mid.account.repo.AccountTypeRepository
import com.kyoulho.mid.exception.MIDException
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class AccountTypeService(
    private val accountTypeRepository: AccountTypeRepository
) {

    // 계좌 종류 생성
    fun createAccountType(dto: CreateAccountTypeDTO): GetAccountTypeDTO {
        val accountType = AccountType(
            name = dto.name,
            description = dto.description,
        )

        dto.fields.forEach {
            accountType.fields.add(
                AccountTypeField(
                    fieldName = it.fieldName,
                    fieldType = it.fieldType,
                    accountType = accountType,
                )
            )
        }

        return accountTypeRepository.save(accountType).toDTO()
    }

    // 모든 계좌 종류 조회
    fun getAccountTypes(): List<GetAccountTypeDTO> {
        return accountTypeRepository.findAll().map(AccountType::toDTO)
    }

    // ID로 계좌 종류 조회
    fun getAccountTypeById(id: String): GetAccountTypeDTO {
        return accountTypeRepository.findById(id)
            .orElseThrow { MIDException(HttpStatus.BAD_REQUEST, "계좌 종류가 존재하지 않습니다. $id") }
            .toDTO()
    }

    // 계좌 종류 업데이트
    @Transactional
    fun updateAccountType(id: String, dto: UpdateAccountTypeDTO): GetAccountTypeDTO {
        val accountType = accountTypeRepository.findById(id)
            .orElseThrow { MIDException(HttpStatus.BAD_REQUEST, "계좌 종류가 존재하지 않습니다. $id") }

        // 계좌 종류 정보 업데이트
        accountType.name = dto.name
        accountType.description = dto.description

        // 기존 필드를 Map으로 변환 (fieldName 기준)
        val existingFieldsMap = accountType.fields
            .associateBy { it.fieldName }
            .toMutableMap()

        // DTO 필드 처리
        dto.fields.forEach { fieldDTO ->
            val existingField = existingFieldsMap[fieldDTO.fieldName]
            if (existingField != null) {
                existingField.fieldType = fieldDTO.fieldType
                existingFieldsMap.remove(fieldDTO.fieldName)
            } else {
                // 새로운 필드 추가
                accountType.fields.add(
                    AccountTypeField(
                        fieldName = fieldDTO.fieldName,
                        fieldType = fieldDTO.fieldType,
                        accountType = accountType
                    )
                )
            }
        }

        // DTO에 없는 기존 필드 삭제
        existingFieldsMap.values.forEach { removedField ->
            accountType.fields.remove(removedField)
        }

        return accountType.toDTO()
    }

    // 계좌 종류 삭제
    fun deleteAccountType(id: String) {
        if (!accountTypeRepository.existsById(id)) {
            throw MIDException(HttpStatus.BAD_REQUEST, "계좌 종류가 존재하지 않습니다. $id")
        }
        accountTypeRepository.deleteById(id)
    }
}
