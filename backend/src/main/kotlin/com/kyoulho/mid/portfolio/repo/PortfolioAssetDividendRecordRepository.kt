package com.kyoulho.mid.portfolio.repo

import com.kyoulho.mid.portfolio.entity.PortfolioAssetDividendRecord
import org.springframework.data.jpa.repository.JpaRepository

interface PortfolioAssetDividendRecordRepository : JpaRepository<PortfolioAssetDividendRecord, String> {
    fun findByIdAndPortfolioAsset_IdAndPortfolioAsset_Portfolio_IdAndPortfolioAsset_Portfolio_User_Id(
        dividendRecordId: String,
        portfolioAssetId: String,
        portfolioId: String,
        userId: String
    ): PortfolioAssetDividendRecord?

}
