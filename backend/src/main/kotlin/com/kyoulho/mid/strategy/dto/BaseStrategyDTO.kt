package com.kyoulho.mid.strategy.dto

import com.kyoulho.mid.const.RebalanceFrequency
import com.kyoulho.mid.const.StrategyType
import com.kyoulho.mid.strategy.entity.Strategy


data class CreateStrategyDTO(
    var type: StrategyType,
    val name: String,
    val rebalanceFrequency: RebalanceFrequency,
)

data class GetStrategyDTO(
    val id: String,
    val name: String,
    val rebalanceFrequency: RebalanceFrequency,
)

typealias UpdateStrategyDTO = CreateStrategyDTO


fun Strategy.toDTO(): GetStrategyDTO {
    return GetStrategyDTO(
        id = this.id,
        name = this.name,
        rebalanceFrequency = this.rebalanceFrequency,
    )
}
