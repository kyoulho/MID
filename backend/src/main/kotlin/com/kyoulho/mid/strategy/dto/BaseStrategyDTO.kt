package com.kyoulho.mid.strategy.dto

import com.kyoulho.mid.const.RebalanceFrequency
import com.kyoulho.mid.const.StrategyType
import com.kyoulho.mid.strategy.AggressiveAsset
import com.kyoulho.mid.strategy.CanaryAsset
import com.kyoulho.mid.strategy.DefensiveAsset
import com.kyoulho.mid.strategy.InvestmentStrategy


data class GetStrategyDTO(
    val alias: String,
    val name: String,
    val type: StrategyType,
    val rebalanceFrequency: RebalanceFrequency,
    val aggressiveAssets: Set<AggressiveAsset>,
    val defensiveAssets: Set<DefensiveAsset>,
    val canaryAssets: Set<CanaryAsset>,
)

fun InvestmentStrategy.toDTO(): GetStrategyDTO {
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