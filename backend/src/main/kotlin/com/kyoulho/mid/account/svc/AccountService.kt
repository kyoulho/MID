package com.kyoulho.mid.account.svc

import com.kyoulho.mid.account.dto.CreateAccountDTO
import com.kyoulho.mid.account.dto.GetAccountDTO
import com.kyoulho.mid.account.dto.UpdateAccountDTO
import com.kyoulho.mid.account.dto.toGetAccountDTO
import com.kyoulho.mid.account.entity.Account
import com.kyoulho.mid.account.repo.AccountRepository
import com.kyoulho.mid.account.repo.AccountTypeRepository
import com.kyoulho.mid.user.repo.UserRepository
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.server.ResponseStatusException

@Service
class AccountService(
    private val accountRepository: AccountRepository,
    private val accountTypeRepository: AccountTypeRepository,
    private val userRepository: UserRepository,
) {

    // 계좌 생성
    @Transactional
    fun createAccount(userId: String, dto: CreateAccountDTO): GetAccountDTO =
        with(dto) {
            val accountType = accountTypeRepository.findById(accountTypeId)
                .orElseThrow { ResponseStatusException(HttpStatus.NOT_FOUND, "AccountType not found") }

            val user = userRepository.findById(userId)
                .orElseThrow { ResponseStatusException(HttpStatus.NOT_FOUND, "MidUser not found") }

            accountRepository.save(
                Account(
                    name = name,
                    description = description,
                    issuer = issuer,
                    number = number,
                    interestRate = interestRate,
                    withdrawalLimit = withdrawalLimit,
                    accountType = accountType,
                    user = user,
                )
            ).toGetAccountDTO()
        }

    // 모든 계좌 조회
    fun getAccounts(userId: String): List<GetAccountDTO> =
        accountRepository.findByUserId(userId).map { it.toGetAccountDTO() }

    // 특정 계좌 조회
    fun getAccountById(userId: String, accountId: String): GetAccountDTO =
        accountRepository.findByIdAndUserId(accountId, userId)
            .orElseThrow { ResponseStatusException(HttpStatus.NOT_FOUND, "Account not found") }
            .toGetAccountDTO()

    // 계좌 업데이트
    @Transactional
    fun updateAccount(userId: String, accountId: String, dto: UpdateAccountDTO): GetAccountDTO {
        val account = accountRepository.findByIdAndUserId(accountId, userId)
            .orElseThrow { ResponseStatusException(HttpStatus.NOT_FOUND, "Account not found") }

        account.apply {
            if (accountType.id != dto.accountTypeId) {
                accountTypeRepository.findById(dto.accountTypeId)
                    .orElseThrow { ResponseStatusException(HttpStatus.NOT_FOUND, "AccountType not found") }
                    .let { accountType = it }
            }
            name = dto.name
            description = dto.description
            issuer = dto.issuer
            number = dto.number
            interestRate = dto.interestRate
            withdrawalLimit = dto.withdrawalLimit
        }

        return accountRepository.save(account).toGetAccountDTO()
    }

    // 계좌 삭제
    @Transactional
    fun deleteAccount(userId: String, accountId: String) {
        accountRepository.findByIdAndUserId(accountId, userId)
            .orElseThrow { ResponseStatusException(HttpStatus.NOT_FOUND, "Account not found") }
            .let { accountRepository.delete(it) }
    }

    fun getAccountTypes() {
        accountTypeRepository.findAll()
    }
}
