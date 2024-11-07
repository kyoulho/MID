package com.kyoulho.mid.account.ctr

import com.kyoulho.mid.account.dto.CreateAccountDTO
import com.kyoulho.mid.account.dto.GetAccountDTO
import com.kyoulho.mid.account.dto.UpdateAccountDTO
import com.kyoulho.mid.account.svc.AccountService
import com.kyoulho.mid.auth.dto.CustomUserDetails
import org.springframework.http.HttpStatus
import org.springframework.security.access.annotation.Secured
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*
import java.util.*

@Secured("isAuthenticated()")
@RestController
@RequestMapping("/api/accounts")
class AccountController(
    private val accountService: AccountService
) {
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun createAccount(
        @AuthenticationPrincipal principal: CustomUserDetails,
        @RequestBody createAccountDTO: CreateAccountDTO
    ): GetAccountDTO {
        return accountService.createAccount(principal.id, createAccountDTO)
    }


    @GetMapping
    fun getAccounts(
        @AuthenticationPrincipal principal: CustomUserDetails,
    ): List<GetAccountDTO> {
        return accountService.getAccounts(principal.id)
    }

    @GetMapping("/{id}")
    fun getAccountById(
        @AuthenticationPrincipal principal: CustomUserDetails,
        @PathVariable id: String
    ): GetAccountDTO {
        return accountService.getAccountById(principal.id, id)
    }

    @PutMapping("/{id}")
    fun updateAccount(
        @AuthenticationPrincipal principal: CustomUserDetails,
        @PathVariable id: String,
        @RequestBody updateAccountDTO: UpdateAccountDTO
    ): GetAccountDTO {
        return accountService.updateAccount(principal.id, id, updateAccountDTO)
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteAccount(
        @AuthenticationPrincipal principal: CustomUserDetails,
        @PathVariable id: String
    ) {
        accountService.deleteAccount(principal.id, id)
    }
}
