package com.kyoulho.mid.portfolio.repo

import com.kyoulho.mid.portfolio.entity.PortfolioAssetTradingRecord
import org.springframework.data.jpa.repository.JpaRepository

interface PortfolioAssetTradingRecordRepository : JpaRepository<PortfolioAssetTradingRecord, String> {
    fun findByIdAndPortfolio_IdAndPortfolio_User_Id()

}
