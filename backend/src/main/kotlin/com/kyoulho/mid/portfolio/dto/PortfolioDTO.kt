package com.kyoulho.mid.portfolio.dto

import com.kyoulho.mid.portfolio.entity.Portfolio
import com.kyoulho.mid.strategy.dto.GetStrategyDTO
import com.kyoulho.mid.strategy.dto.toDTO

data class CreatePortfolioDTO(
    val strategyId: String?,
    val assets: List<CreatePortfolioAssetDTO>,
    val description: String,
)

data class UpdatePortfolioDTO(
    val strategyId: String?,
    val description: String,
)

data class GetPortfolioDTO(
    val id: String,
    val strategy: GetStrategyDTO?,
    val assets: List<GetPortfolioAssetDTO>,
    val description: String,
)

fun Portfolio.toDTO(): GetPortfolioDTO {
    return GetPortfolioDTO(
        id = this.id,
        strategy = this.strategy?.toDTO(),
        assets = this.assets.map { it.toDTO() },
        description = this.description
    )
}
