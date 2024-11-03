package com.kyoulho.mid.strategy.dto

import com.kyoulho.mid.const.RebalanceFrequency
import com.kyoulho.mid.const.StrategyType
import com.kyoulho.mid.const.Ticker
import com.kyoulho.mid.strategy.entity.Asset
import com.kyoulho.mid.strategy.entity.Strategy


data class CreateStrategyDTO(
    var type: StrategyType,
    val alias: String,
    val name: String,
    val rebalanceFrequency: RebalanceFrequency,
    val rules: List<String>,
    val aggressiveAssets: Set<AssetDTO>,
    val defensiveAssets: Set<AssetDTO>,
    val canaryAssets: Set<AssetDTO>
)

data class GetStrategyDTO(
    val id: String,
    var type: StrategyType,
    val alias: String,
    val name: String,
    val rebalanceFrequency: RebalanceFrequency,
    val rules: List<String>,
    val aggressiveAssets: Set<AssetDTO>,
    val defensiveAssets: Set<AssetDTO>,
    val canaryAssets: Set<AssetDTO>
)

typealias UpdateStrategyDTO = CreateStrategyDTO

data class AssetDTO(
    val name: String,
    val ticker: Ticker
)

fun Asset.toDTO(): AssetDTO {
    return AssetDTO(
        name = this.name,
        ticker = this.ticker
    )
}

fun Strategy.toDTO(): GetStrategyDTO {
    return GetStrategyDTO(
        id = this.id,
        alias = this.alias,
        name = this.name,
        type = this.type,
        rebalanceFrequency = this.rebalanceFrequency,
        rules = this.rules,
        aggressiveAssets = this.aggressiveAssets.map { it.toDTO() }.toSet(),
        defensiveAssets = this.defensiveAssets.map { it.toDTO() }.toSet(),
        canaryAssets = this.canaryAssets.map { it.toDTO() }.toSet()
    )
}
