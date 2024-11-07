package com.kyoulho.mid.portfolio.ctr

import com.kyoulho.mid.auth.dto.CustomUserDetails
import com.kyoulho.mid.portfolio.dto.CreatePortfolioDTO
import com.kyoulho.mid.portfolio.dto.GetPortfolioDTO
import com.kyoulho.mid.portfolio.dto.UpdatePortfolioDTO
import com.kyoulho.mid.portfolio.svc.PortfolioService
import org.springframework.http.HttpStatus
import org.springframework.security.access.annotation.Secured
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*

@Secured("isAuthenticated()")
@RestController
@RequestMapping("/api/portfolios")
class PortfolioController(
    private val portfolioService: PortfolioService
) {
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun createAccount(
        @AuthenticationPrincipal principal: CustomUserDetails,
        @RequestBody dto: CreatePortfolioDTO
    ): GetPortfolioDTO {
        return portfolioService.createPortfolio(principal.id, dto)
    }


    @GetMapping
    fun getAccounts(
        @AuthenticationPrincipal principal: CustomUserDetails,
    ): List<GetPortfolioDTO> {
        return portfolioService.getPortfolio(principal.id)
    }

    @GetMapping("/{id}")
    fun getAccountById(
        @AuthenticationPrincipal principal: CustomUserDetails,
        @PathVariable id: String
    ): GetPortfolioDTO {
        return portfolioService.getPortfolioById(principal.id, id)
    }

    @PutMapping("/{id}")
    fun updateAccount(
        @AuthenticationPrincipal principal: CustomUserDetails,
        @PathVariable id: String,
        @RequestBody dto: UpdatePortfolioDTO
    ): GetPortfolioDTO {
        return portfolioService.updatePortfolio(principal.id, id, dto)
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteAccount(
        @AuthenticationPrincipal principal: CustomUserDetails,
        @PathVariable id: String
    ) {
        portfolioService.deletePortfolio(principal.id, id)
    }
}