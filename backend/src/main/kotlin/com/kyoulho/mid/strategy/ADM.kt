package com.kyoulho.mid.strategy

import com.kyoulho.mid.const.RebalanceFrequency
import com.kyoulho.mid.const.StrategyType
import com.kyoulho.mid.strategy.svc.Calculations
import com.kyoulho.mid.strategy.svc.IPriceDataProvider
import org.springframework.stereotype.Component
import java.time.LocalDate

@Component
class ADM(
    dataProvider: IPriceDataProvider
) : AbstractStrategy(dataProvider) {
    override val alias = "ADM"
    override val name = "Accelerated Dual Momentum"
    override val type = StrategyType.DAA
    override val rebalanceFrequency = RebalanceFrequency.MONTHLY
    override val aggressiveAssets = setOf(
        Asset(
            primaryAssetTicker = "SPY",
            alternativeAssetTicker = "VOO",
        ),
        Asset(
            primaryAssetTicker = "SCZ",
            alternativeAssetTicker = "VSS",
        ),
    )
    override val defensiveAssets = setOf(
        Asset(
            primaryAssetTicker = "TLT",
            alternativeAssetTicker = null,
        )
    )
    override val canaryAssets = emptySet<Asset>()

    override fun execute(
    ): Set<SelectedAsset> {
        val endDate = LocalDate.now()
        val startDate = endDate.minusMonths(6) // 필요한 데이터 기간 설정

        // 공격 자산들의 모멘텀 스코어 계산
        val momentumScores = aggressiveAssets.associateWith { asset ->
            val priceData = dataProvider.getHistoricalPrices(asset.primaryAssetTicker, startDate, endDate)
            Calculations.calculateCurrentMomentumScore(priceData, mapOf(1 to 1.0, 3 to 1.0, 6 to 1.0))
        }

        // 모든 모멘텀 스코어가 음수인지 확인
        val allNegative = momentumScores.values.all { it < 0 }

        return if (allNegative) {
            // 방어 자산 선택
            val defensiveAsset = defensiveAssets.first()
            setOf(SelectedAsset(defensiveAsset.primaryAssetTicker, defensiveAsset.alternativeAssetTicker, 1.0))
        } else {
            // 가장 높은 모멘텀 스코어를 가진 자산 선택
            val bestAsset = momentumScores.maxByOrNull { it.value }!!.key
            setOf(SelectedAsset(bestAsset.primaryAssetTicker, bestAsset.alternativeAssetTicker, 1.0))
        }
    }
}