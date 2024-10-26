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
class AccountService(
    private val accountRepository: AccountRepository,
    private val userRepository: UserRepository,
) {

    // 계좌 생성
    @Transactional
    fun createAccount(userId: String, dto: CreateAccountDTO): GetAccountDTO {

        val user = userRepository.findById(userId)
            .orElseThrow { ResponseStatusException(HttpStatus.FORBIDDEN, "존재하지 않는 유저의 요청") }

        val account = Account(
            name = dto.name,
            description = dto.description,
            issuer = dto.issuer,
            number = dto.number,
            interestRate = dto.interestRate,
            accountType = dto.accountType,
            user = user,
        )
        TODO()
    }

    // 모든 계좌 조회
    fun getAccounts(userId: String): List<GetAccountDTO> =
        accountRepository.findByUserId(userId).map { it.toDTO() }

    // 특정 계좌 조회
    fun getAccountById(userId: String, accountId: String): GetAccountDTO =
        accountRepository.findByIdAndUserId(accountId, userId)
            .orElseThrow { ResponseStatusException(HttpStatus.NOT_FOUND, "Account not found") }
            .toDTO()

    // 계좌 업데이트
    @Transactional
    fun updateAccount(userId: String, accountId: String, dto: UpdateAccountDTO): GetAccountDTO {
        val account = accountRepository.findByIdAndUserId(accountId, userId)
            .orElseThrow { ResponseStatusException(HttpStatus.NOT_FOUND, "Account not found") }

        TODO()

    }

    // 계좌 삭제
    @Transactional
    fun deleteAccount(userId: String, accountId: String) {
        accountRepository.findByIdAndUserId(accountId, userId)
            .orElseThrow { ResponseStatusException(HttpStatus.NOT_FOUND, "Account not found") }
            .let { accountRepository.delete(it) }
    }
}
