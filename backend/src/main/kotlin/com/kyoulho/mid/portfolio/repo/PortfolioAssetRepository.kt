package com.kyoulho.mid.portfolio.repo

import com.kyoulho.mid.portfolio.entity.PortfolioAsset
import org.springframework.data.jpa.repository.JpaRepository

interface PortfolioAssetRepository : JpaRepository<PortfolioAsset, String> {
    fun findByIdAndPortfolio_IdAndPortfolio_User_Id(
        id: String,
        portfolioId: String,
        userId: String
    ): PortfolioAsset?
}
