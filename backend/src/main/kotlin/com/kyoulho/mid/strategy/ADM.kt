package com.kyoulho.mid.strategy

import com.kyoulho.mid.const.RebalanceFrequency
import com.kyoulho.mid.const.StrategyType
import org.springframework.stereotype.Component

@Component
class ADM : InvestmentStrategy() {
    override val alias = "ADM"
    override val name = "Accelerated Dual Momentum"
    override val type = StrategyType.DAA
    override val rebalanceFrequency = RebalanceFrequency.MONTHLY
    override val aggressiveAssets = setOf(
        AggressiveAsset(
            primaryAssetTicker = "SPY",
            alternativeAssetTicker = "VOO",
            momentumScoreMethod = MomentumScoreType.S136
        ),
        AggressiveAsset(
            primaryAssetTicker = "SCZ",
            alternativeAssetTicker = "VSS",
            momentumScoreMethod = MomentumScoreType.S136
        ),
    )
    override val defensiveAssets = setOf(
        DefensiveAsset(
            primaryAssetTicker = "TLT",
            alternativeAssetTicker = null,
            momentumScoreMethod = null,
        )
    )
    override val canaryAssets = setOf<CanaryAsset>()

    override fun executeStrategy(): () -> Set<SelectedAsset> {
        TODO("Not yet implemented")
    }

}

