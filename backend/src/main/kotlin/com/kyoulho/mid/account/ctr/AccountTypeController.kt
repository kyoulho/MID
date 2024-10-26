package com.kyoulho.mid.account.ctr

import com.kyoulho.mid.account.dto.AccountTypeDTO
import com.kyoulho.mid.account.dto.toDTO
import com.kyoulho.mid.account.enum.AccountTypeEnum
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/account-types")
class AccountTypeController {

    @GetMapping
    fun getAccountTypes(): List<AccountTypeDTO> {
        return AccountTypeEnum.values().map { it.toDTO() }
    }

}

