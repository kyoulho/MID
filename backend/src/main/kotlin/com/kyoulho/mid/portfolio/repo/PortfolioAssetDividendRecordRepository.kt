package com.kyoulho.mid.portfolio.repo

import com.kyoulho.mid.portfolio.entity.PortfolioAssetDividendRecord
import com.kyoulho.mid.portfolio.entity.PortfolioAssetTradingRecord
import org.springframework.data.jpa.repository.JpaRepository

interface PortfolioAssetDividendRecordRepository : JpaRepository<PortfolioAssetDividendRecord, String> {

}
