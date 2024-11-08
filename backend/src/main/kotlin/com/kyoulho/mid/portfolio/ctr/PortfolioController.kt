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
    fun createPortfolio(
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

    @GetMapping("/{portfolioId}")
    fun getPortfolioById(
        @AuthenticationPrincipal principal: CustomUserDetails,
        @PathVariable portfolioId: String
    ): GetPortfolioDTO {
        return portfolioService.getPortfolioById(principal.id, portfolioId)
    }

    @PutMapping("/{portfolioId}")
    fun updatePortfolio(
        @AuthenticationPrincipal principal: CustomUserDetails,
        @PathVariable portfolioId: String,
        @RequestBody dto: UpdatePortfolioDTO
    ): GetPortfolioDTO {
        return portfolioService.updatePortfolio(principal.id, portfolioId, dto)
    }

    @DeleteMapping("/{portfolioId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deletePortfolio(
        @AuthenticationPrincipal principal: CustomUserDetails,
        @PathVariable portfolioId: String
    ) {
        portfolioService.deletePortfolio(principal.id, portfolioId)
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
        return portfolioService.deletePortfolioAsset(principal.id, portfolioId, assetId)
    }

    @PostMapping("/{portfolioId}/asset/{assetId}/trading-record")
    @ResponseStatus(HttpStatus.CREATED)
    fun createAssetTradingRecord(
        @AuthenticationPrincipal principal: CustomUserDetails,
        @PathVariable portfolioId: String,
        @PathVariable assetId: String,
        @RequestBody dto: CreateAssetTradingRecordDTO
    ): GetAssetTradingRecordDTO {
        return portfolioService.createAssetTradingRecord(
            principal.id,
            portfolioId,
            assetId,
            dto
        )
    }

    @PutMapping("/{portfolioId}/asset/{assetId}/trading-record/{tradingRecordId}")
    fun updateAssetTradingRecord(
        @AuthenticationPrincipal principal: CustomUserDetails,
        @PathVariable portfolioId: String,
        @PathVariable assetId: String,
        @PathVariable tradingRecordId: String,
        @RequestBody dto: CreateAssetTradingRecordDTO
    ): GetAssetTradingRecordDTO {
        return portfolioService.updateAssetTradingRecord(
            principal.id,
            portfolioId,
            assetId,
            tradingRecordId,
            dto
        )
    }

    @DeleteMapping("/{portfolioId}/asset/{assetId}/trading-record/{tradingRecordId}")
    fun deleteAssetTradingRecord(
        @AuthenticationPrincipal principal: CustomUserDetails,
        @PathVariable portfolioId: String,
        @PathVariable assetId: String,
        @PathVariable tradingRecordId: String
    ) {
        return portfolioService.deleteAssetTradingRecord(
            principal.id,
            portfolioId,
            assetId,
            tradingRecordId,
        )
    }

    @PostMapping("/{portfolioId}/asset/{assetId}/dividend-record")
    @ResponseStatus(HttpStatus.CREATED)
    fun createAssetDividendRecord(
        @AuthenticationPrincipal principal: CustomUserDetails,
        @PathVariable portfolioId: String,
        @PathVariable assetId: String,
        @RequestBody dto: CreateAssetDividendRecordDTO
    ): GetAssetDividendRecordDTO {
        return portfolioService.createAssetDividendRecord(
            principal.id,
            portfolioId,
            assetId,
            dto
        )
    }

    @PutMapping("/{portfolioId}/asset/{assetId}/dividend-record/{dividendRecordId}")
    fun updateAssetDividendRecord(
        @AuthenticationPrincipal principal: CustomUserDetails,
        @PathVariable portfolioId: String,
        @PathVariable assetId: String,
        @PathVariable dividendRecordId: String,
        @RequestBody dto: UpdateAssetDividendRecordDTO
    ): GetAssetDividendRecordDTO {
        return portfolioService.updateAssetDividendRecord(
            principal.id,
            portfolioId,
            assetId,
            dividendRecordId,
            dto
        )
    }

    @DeleteMapping("/{portfolioId}/asset/{assetId}/dividend-record/{dividendRecordId}")
    fun deleteAssetDividendRecord(
        @AuthenticationPrincipal principal: CustomUserDetails,
        @PathVariable portfolioId: String,
        @PathVariable assetId: String,
        @PathVariable dividendRecordId: String
    ) {
        return portfolioService.deleteAssetDividendRecord(
            principal.id,
            portfolioId,
            assetId,
            dividendRecordId,
        )
    }
}