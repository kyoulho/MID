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
    @PreAuthorize("hasRole('USER')")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun createAccount(
        @RequestUserId userId: String,
        @RequestBody createAccountDTO: CreateAccountDTO
    ): GetAccountDTO {
        return accountService.createAccount(userId, createAccountDTO)
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping
    fun getAccounts(
        @RequestUserId userId: String
    ): List<GetAccountDTO> {
        return accountService.getAccounts(userId)
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/{id}")
    fun getAccountById(
        @RequestUserId userId: String,
        @PathVariable id: String
    ): GetAccountDTO {
        return accountService.getAccountById(userId, id)
    }

    @PreAuthorize("hasRole('USER')")
    @PutMapping("/{id}")
    fun updateAccount(
        @RequestUserId userId: String,
        @PathVariable id: String,
        @RequestBody updateAccountDTO: UpdateAccountDTO
    ): GetAccountDTO {
        return accountService.updateAccount(userId, id, updateAccountDTO)
    }

    @PreAuthorize("hasRole('USER')")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteAccount(
        @RequestUserId userId: String,
        @PathVariable id: String
    ) {
        accountService.deleteAccount(userId, id)
    }
}
