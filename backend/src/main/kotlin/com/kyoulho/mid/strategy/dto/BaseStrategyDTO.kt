package com.kyoulho.mid.strategy.dto

import com.kyoulho.mid.const.RebalanceFrequency
import com.kyoulho.mid.const.StrategyType
import com.kyoulho.mid.strategy.AbstractStrategy
import com.kyoulho.mid.strategy.Asset


data class GetStrategyDTO(
    val alias: String,
    val name: String,
    val type: StrategyType,
    val rebalanceFrequency: RebalanceFrequency,
    val aggressiveAssets: Set<Asset>,
    val defensiveAssets: Set<Asset>,
    val canaryAssets: Set<Asset>,
)

fun AbstractStrategy.toDTO(): GetStrategyDTO {
    return GetStrategyDTO(
        alias = this.alias,
        name = this.name,
        type = this.type,
        rebalanceFrequency = this.rebalanceFrequency,
        aggressiveAssets = this.aggressiveAssets,
        defensiveAssets = this.defensiveAssets,
        canaryAssets = this.canaryAssets
    )
}