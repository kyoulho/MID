package com.kyoulho.mid.strategy.dto

import com.kyoulho.mid.const.RebalanceFrequency
import com.kyoulho.mid.const.StrategyType
import com.kyoulho.mid.strategy.entity.Strategy


data class CreateStrategyDTO(
    var name: String,
    var type: StrategyType,
    var description: String? = null,
    var script: String? = null,
    var rebalanceFrequency: RebalanceFrequency
)

data class GetStrategyDTO(
    var id: String,
    var name: String,
    var type: StrategyType,
    var description: String? = null,
    var script: String? = null,
    var rebalanceFrequency: RebalanceFrequency
)

data class UpdateStrategyDTO(
    var name: String,
    var type: StrategyType,
    var description: String? = null,
    var script: String? = null,
    var rebalanceFrequency: RebalanceFrequency,
)

fun Strategy.toDTO(): GetStrategyDTO {
    return GetStrategyDTO(
        id = this.id,
        name = this.name,
        type = this.type,
        description = this.description,
        script = this.script,
        rebalanceFrequency = this.rebalanceFrequency
    )
}