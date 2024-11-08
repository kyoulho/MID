package com.kyoulho.mid.portfolio.dto

import com.kyoulho.mid.const.TradingType
import java.time.LocalDate

data class CreateAssetTradingRecordDTO(
    val accountId: String,
    val portfolioAssetId: String,
    val tradingType: TradingType,
    val quantity: Long,
    val price: Long,
    val tradingDate: LocalDate
)

typealias UpdateAssetTradingRecordDTO = CreateAssetDividendRecordDTO

data class GetAssetTradingRecordDTO(
    val id: String,
    val tradingType: TradingType,
    val quantity: Long,
    val price: Long,
    val tradingDate: LocalDate
)