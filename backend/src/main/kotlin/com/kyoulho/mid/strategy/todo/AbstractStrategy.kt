package com.kyoulho.mid.strategy.todo

import com.kyoulho.mid.const.RebalanceFrequency
import com.kyoulho.mid.const.StrategyType
import com.kyoulho.mid.const.Ticker

// 투자 전략의 추상 클래스 - 구체적인 전략 클래스들이 상속하여 사용
abstract class AbstractStrategy(
    protected val dataProvider: IPriceDataProvider
) {
    abstract val alias: String
    abstract val name: String
    abstract val type: StrategyType
    abstract val rebalanceFrequency: RebalanceFrequency
    abstract val aggressiveAssets: Set<Asset>
    abstract val defensiveAssets: Set<Asset>
    abstract val canaryAssets: Set<Asset>
    abstract fun execute(): Set<SelectedAsset>
}

// 계산에 사용되는 자산
data class Asset(
    val primaryAssetTicker: Ticker,
    val alternativeAssetTicker: Ticker? = null,
)

// 최종 선택된 자산 클래스
data class SelectedAsset(
    val selectedAssetTicker: Ticker,
    val backupAssetTicker: Ticker? = null,
    val allocationRatio: Double
)
