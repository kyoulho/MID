package com.kyoulho.mid.portfolio.dto

import com.kyoulho.mid.const.Ticker
import com.kyoulho.mid.portfolio.entity.PortfolioAsset

data class CreatePortfolioAssetDTO(
    val intendedAsset: Ticker,
    val targetRatio: Double
)

typealias UpdatePortfolioAssetDTO = CreatePortfolioAssetDTO

data class GetPortfolioAssetDTO(
    val id: String,
    val intendedAsset: Ticker,
    val targetRatio: Double
)

fun PortfolioAsset.toDTO(): GetPortfolioAssetDTO {
    TODO("Not yet implemented")
}