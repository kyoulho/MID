package com.kyoulho.mid.portfolio.ctr

import com.kyoulho.mid.auth.dto.CustomUserDetails
import com.kyoulho.mid.portfolio.dto.*
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
    fun getPortfolio(
        @AuthenticationPrincipal principal: CustomUserDetails,
    ): List<GetPortfolioDTO> {
        return portfolioService.getPortfolio(principal.id)
    }

    @GetMapping("/{id}")
    fun getPortfolioById(
        @AuthenticationPrincipal principal: CustomUserDetails,
        @PathVariable id: String
    ): GetPortfolioDTO {
        return portfolioService.getPortfolioById(principal.id, id)
    }

    @PutMapping("/{id}")
    fun updatePortfolio(
        @AuthenticationPrincipal principal: CustomUserDetails,
        @PathVariable id: String,
        @RequestBody dto: UpdatePortfolioDTO
    ): GetPortfolioDTO {
        return portfolioService.updatePortfolio(principal.id, id, dto)
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deletePortfolio(
        @AuthenticationPrincipal principal: CustomUserDetails,
        @PathVariable id: String
    ) {
        portfolioService.deletePortfolio(principal.id, id)
    }


    @PutMapping("/{portfolioId}/asset/{assetId}")
    fun updatePortfolioAsset(
        @AuthenticationPrincipal principal: CustomUserDetails,
        @PathVariable portfolioId: String,
        @PathVariable assetId: String,
        @RequestBody dto: UpdatePortfolioAssetDTO
    ): GetPortfolioAssetDTO {
        return portfolioService.updatePortfolioAsset(principal.id, portfolioId, assetId, dto)
    }

    @DeleteMapping("/{portfolioId}/asset/{assetId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deletePortfolioAsset(
        @AuthenticationPrincipal principal: CustomUserDetails,
        @PathVariable portfolioId: String,
        @PathVariable assetId: String,
    ) {
        portfolioService.deletePortfolioAsset(principal.id, portfolioId, assetId)
    }
}