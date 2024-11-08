package com.kyoulho.mid.portfolio.repo

import com.kyoulho.mid.portfolio.entity.PortfolioAssetTradingRecord
import org.springframework.data.jpa.repository.JpaRepository

interface PortfolioAssetTradingRecordRepository : JpaRepository<PortfolioAssetTradingRecord, String> {
    fun findByIdAndPortfolioAsset_IdAndPortfolioAsset_Portfolio_IdAndPortfolioAsset_Portfolio_User_Id(
        tradingRecordId: String,
        portfolioAssetId: String,
        portfolioId: String,
        userId: String
    ): PortfolioAssetTradingRecord?

}
