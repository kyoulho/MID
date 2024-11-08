package com.kyoulho.mid.portfolio.dto

import com.kyoulho.mid.portfolio.entity.PortfolioAsset

data class CreatePortfolioAssetDTO(
    val ticker: String,
    val targetRatio: Double
)

typealias UpdatePortfolioAssetDTO = CreatePortfolioAssetDTO

data class GetPortfolioAssetDTO(
    val id: String,
    val ticker: String,
    val targetRatio: Double,

)

fun PortfolioAsset.toDTO(): GetPortfolioAssetDTO {
    return GetPortfolioAssetDTO(
        this.id, this.ticker, this.targetRatio
    )
}