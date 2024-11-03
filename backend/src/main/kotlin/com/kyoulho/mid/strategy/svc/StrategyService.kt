package com.kyoulho.mid.strategy.svc

import com.kyoulho.mid.strategy.AbstractStrategy
import com.kyoulho.mid.strategy.SelectedAsset
import com.kyoulho.mid.strategy.dto.GetStrategyDTO
import com.kyoulho.mid.strategy.dto.toDTO
import org.springframework.stereotype.Service

@Service
class StrategyService(
    private val strategies: List<AbstractStrategy>,
    private val dataProvider: IPriceDataProvider,
) {
    fun getStrategies(): List<GetStrategyDTO> {
        return strategies.map { it.toDTO() }
    }

    fun getStrategy(alias: String): GetStrategyDTO {
        return strategies.first { it.alias == alias }.toDTO()
    }

    fun executeStrategy(alias: String): Set<SelectedAsset> {
        val strategy = strategies.first { it.alias == alias }
        return strategy.execute()
    }
}