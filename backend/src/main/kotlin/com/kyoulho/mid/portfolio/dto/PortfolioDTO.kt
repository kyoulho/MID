package com.kyoulho.mid.portfolio.dto

import com.kyoulho.mid.const.Ticker
import com.kyoulho.mid.portfolio.entity.Portfolio

data class CreatePortfolioDTO(
    val strategyId: String?,
    val assets: List<CreatePortfolioAssetDTO>,
    val description: String,
)

data class UpdatePortfolioDTO(
    val strategyId: String?,
    val assets: List<UpdatePortfolioAssetDTO>,
    val description: String,
)

data class GetPortfolioDTO(
    val id: String,
    val strategyId: String?,
    val assets: List<GetPortfolioAssetDTO>,
    val description: String,
)

data class CreatePortfolioAssetDTO(
    val intendedAsset: Ticker,
    val targetRatio: Double
)

typealias UpdatePortfolioAssetDTO = GetPortfolioAssetDTO

data class GetPortfolioAssetDTO(
    val id: String,
    val intendedAsset: Ticker,
    val targetRatio: Double
)

fun Portfolio.toDTO(): GetPortfolioDTO {

}