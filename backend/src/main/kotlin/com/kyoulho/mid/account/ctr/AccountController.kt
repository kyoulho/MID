package com.kyoulho.mid.account.ctr

import com.kyoulho.mid.account.dto.CreateAccountDTO
import com.kyoulho.mid.account.dto.GetAccountDTO
import com.kyoulho.mid.account.dto.UpdateAccountDTO
import com.kyoulho.mid.account.svc.AccountService
import com.kyoulho.mid.auth.annotation.RequestUserId
import org.springframework.http.HttpStatus
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/api/accounts")
class AccountController(
    private val accountService: AccountService
) {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun createAccount(
        @RequestUserId userId: String,
        @RequestBody createAccountDTO: CreateAccountDTO
    ): GetAccountDTO {
        return accountService.createAccount(userId, createAccountDTO)
    }

    // 모든 계좌 조회
    @GetMapping
    fun getAccounts(
        @RequestUserId userId: String
    ): List<GetAccountDTO> {
        return accountService.getAccounts(userId)
    }

    // ID로 계좌 조회
    @GetMapping("/{id}")
    fun getAccountById(
        @RequestUserId userId: String,
        @PathVariable id: String
    ): GetAccountDTO {
        return accountService.getAccountById(userId, id)
    }

    // 계좌 업데이트
    @PutMapping("/{id}")
    fun updateAccount(
        @RequestUserId userId: String,
        @PathVariable id: String,
        @RequestBody updateAccountDTO: UpdateAccountDTO
    ): GetAccountDTO {
        return accountService.updateAccount(userId, id, updateAccountDTO)
    }

    // 계좌 삭제
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteAccount(
        @RequestUserId userId: String,
        @PathVariable id: String
    ) {
        accountService.deleteAccount(userId, id)
    }
}
