package com.kyoulho.mid.account.svc

import com.kyoulho.mid.account.dto.CreateAccountDTO
import com.kyoulho.mid.account.dto.GetAccountDTO
import com.kyoulho.mid.account.dto.UpdateAccountDTO
import com.kyoulho.mid.account.dto.toDTO
import com.kyoulho.mid.account.entity.Account
import com.kyoulho.mid.account.repo.AccountRepository
import com.kyoulho.mid.user.repo.UserRepository
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.server.ResponseStatusException

@Service
@Transactional
class AccountService(
    private val accountRepository: AccountRepository,
    private val userRepository: UserRepository,
) {

    fun createAccount(userId: String, dto: CreateAccountDTO): GetAccountDTO {
        val user = userRepository.findById(userId)
            .orElseThrow { ResponseStatusException(HttpStatus.FORBIDDEN, "존재하지 않는 유저의 요청") }
        return Account(
            name = dto.name,
            description = dto.description,
            issuer = dto.issuer,
            number = dto.number,
            interestRate = dto.interestRate,
            accountType = dto.accountType,
            fields = dto.fields.toMutableMap(),
            user = user,
        ).apply {
            accountRepository.save(this)
        }.toDTO()
    }

    @Transactional(readOnly = true)
    fun getAccounts(userId: String): List<GetAccountDTO> =
        accountRepository.findByUserId(userId).map { it.toDTO() }

    @Transactional(readOnly = true)
    fun getAccountById(userId: String, accountId: String): GetAccountDTO =
        accountRepository.findByIdAndUserId(accountId, userId)
            .orElseThrow { ResponseStatusException(HttpStatus.NOT_FOUND, "계좌를 찾을 수 없습니다.") }
            .toDTO()

    fun updateAccount(userId: String, accountId: String, dto: UpdateAccountDTO): GetAccountDTO {
        return accountRepository.findByIdAndUserId(accountId, userId)
            .orElseThrow { ResponseStatusException(HttpStatus.NOT_FOUND, "계좌를 찾을 수 없습니다.") }
            .apply {
                name = dto.name
                description = dto.description
                issuer = dto.issuer
                number = dto.number
                interestRate = dto.interestRate
                accountType = dto.accountType
                fields = dto.fields.toMutableMap()
            }.toDTO()
    }

    fun deleteAccount(userId: String, accountId: String) {
        accountRepository.findByIdAndUserId(accountId, userId)
            .orElseThrow { ResponseStatusException(HttpStatus.NOT_FOUND, "계좌를 찾을 수 없습니다.") }
            .let { accountRepository.delete(it) }
    }
}
