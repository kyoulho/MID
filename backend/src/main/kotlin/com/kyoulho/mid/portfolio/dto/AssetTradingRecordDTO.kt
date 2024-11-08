package com.kyoulho.mid.portfolio.dto

import com.kyoulho.mid.account.dto.GetAccountDTO
import com.kyoulho.mid.account.dto.toDTO
import com.kyoulho.mid.const.TradingType
import com.kyoulho.mid.portfolio.entity.PortfolioAssetTradingRecord
import java.time.LocalDate

data class CreateAssetTradingRecordDTO(
    val accountId: String,
    val tradingType: TradingType,
    val quantity: Long,
    val price: Long,
    val tradingDate: LocalDate
)

typealias UpdateAssetTradingRecordDTO = CreateAssetTradingRecordDTO

data class GetAssetTradingRecordDTO(
    val id: String,
    val account: GetAccountDTO,
    val tradingType: TradingType,
    val quantity: Long,
    val price: Long,
    val tradingDate: LocalDate
)

fun PortfolioAssetTradingRecord.toDTO(): GetAssetTradingRecordDTO {
    return GetAssetTradingRecordDTO(
        id, account.toDTO(), tradingType, quantity, price, tradingDate
    )
}