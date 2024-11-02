package com.kyoulho.mid.strategy.svc

import com.kyoulho.mid.strategy.InvestmentStrategy
import com.kyoulho.mid.strategy.dto.GetStrategyDTO
import com.kyoulho.mid.strategy.dto.toDTO
import org.springframework.stereotype.Service

@Service
class StrategyService(
    private val strategies: List<InvestmentStrategy>
) {
    fun getStrategies(): List<GetStrategyDTO> {
        return strategies.map { it.toDTO() }
    }

    fun getStrategy(alias: String): GetStrategyDTO {
        return strategies.first { it.alias == alias }.toDTO()

    }
}