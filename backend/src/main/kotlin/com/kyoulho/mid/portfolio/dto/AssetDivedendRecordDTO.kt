package com.kyoulho.mid.portfolio.dto

import com.kyoulho.mid.account.dto.GetAccountDTO
import com.kyoulho.mid.account.dto.toDTO
import com.kyoulho.mid.portfolio.entity.PortfolioAssetDividendRecord
import java.time.LocalDate

data class CreateAssetDividendRecordDTO(
    val accountId: String,
    val amount: Long,
    val dividendDate: LocalDate
)

typealias UpdateAssetDividendRecordDTO = CreateAssetDividendRecordDTO

data class GetAssetDividendRecordDTO(
    val id: String,
    val account: GetAccountDTO,
    val amount: Long,
    val dividendDate: LocalDate
)

fun PortfolioAssetDividendRecord.toDTO(): GetAssetDividendRecordDTO {
    return GetAssetDividendRecordDTO(
        id, account.toDTO(), amount, dividendDate
    )
}