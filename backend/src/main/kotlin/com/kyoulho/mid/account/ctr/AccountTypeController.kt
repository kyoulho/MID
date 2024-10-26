package com.kyoulho.mid.account.ctr

import com.kyoulho.mid.account.dto.CreateAccountTypeDTO
import com.kyoulho.mid.account.dto.GetAccountTypeDTO
import com.kyoulho.mid.account.dto.UpdateAccountTypeDTO
import com.kyoulho.mid.account.svc.AccountTypeService
import org.springframework.http.HttpStatus
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/account-types")
class AccountTypeController(
    private val accountTypeService: AccountTypeService
) {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('ADMIN')")
    fun createAccountType(
         @RequestBody createAccountTypeDTO: CreateAccountTypeDTO
    ): GetAccountTypeDTO {
        return accountTypeService.createAccountType(createAccountTypeDTO)
    }

    @GetMapping
    fun getAccountTypes(): List<GetAccountTypeDTO> {
        return accountTypeService.getAccountTypes()
    }

    @GetMapping("/{id}")
    fun getAccountTypeById(
        @PathVariable id: String
    ): GetAccountTypeDTO {
        return accountTypeService.getAccountTypeById(id)
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    fun updateAccountType(
        @PathVariable id: String,
         @RequestBody updateAccountTypeDTO: UpdateAccountTypeDTO
    ): GetAccountTypeDTO {
        return accountTypeService.updateAccountType(id, updateAccountTypeDTO)
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasRole('ADMIN')")
    fun deleteAccountType(
        @PathVariable id: String
    ) {
        accountTypeService.deleteAccountType(id)
    }
}
