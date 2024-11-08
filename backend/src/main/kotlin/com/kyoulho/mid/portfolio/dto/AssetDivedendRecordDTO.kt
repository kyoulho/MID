package com.kyoulho.mid.portfolio.dto

import com.kyoulho.mid.const.TradingType
import java.time.LocalDate

data class CreateAssetDividendRecordDTO(
    val accountId: String,
    val portfolioAssetId: String,
    val amount: Long,
    val dividendDate: LocalDate
)

typealias UpdateAssetDividendRecordDTO = CreateAssetDividendRecordDTO

data class GetAssetDividendRecordDTO(
    val id: String,
    val tradingType: TradingType,
    val quantity: Long,
    val amount: Long,
    val dividendDate: LocalDate
)