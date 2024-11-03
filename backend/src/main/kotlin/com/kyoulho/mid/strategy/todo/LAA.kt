package com.kyoulho.mid.strategy.todo

import com.kyoulho.mid.const.RebalanceFrequency
import com.kyoulho.mid.const.StrategyType
import java.time.LocalDate

class LAA(dataProvider: IPriceDataProvider) : AbstractStrategy(dataProvider) {
    override val alias: String
        get() = "LAA"
    override val name: String
        get() = "Lethargic Asset Allocation"
    override val type: StrategyType
        get() = StrategyType.DAA
    override val rebalanceFrequency: RebalanceFrequency
        get() = RebalanceFrequency.ANNUAL
    override val aggressiveAssets: Set<Asset>
        get() = setOf(
            Asset("IWD")
        )
    override val defensiveAssets: Set<Asset>
        get() = setOf()
    override val canaryAssets: Set<Asset>
        get() = setOf()

    override fun execute(): Set<SelectedAsset> {
        val result = mutableSetOf(
            SelectedAsset("IWD", null, 0.25),
            SelectedAsset("GLD", null, 0.25),
            SelectedAsset("IEF", null, 0.25),
        )

        val endDate = LocalDate.now()
        val startDate = endDate.minusDays(200)

        // 가격 데이터와 실업률 데이터 가져오기
        val priceData = dataProvider.getHistoricalPrices("SPY", startDate, endDate)
        val usUnemploymentRate = dataProvider.fetchUSUnemploymentRate(startDate, endDate)

        // 200일 SMA와 실업률 SMA 계산
        val sma200 = Calculations.calculateCurrentSimpleMovingAverage(priceData, 200)
        val rate200 = calculateMonthlySMA(usUnemploymentRate, 6)  // 6개월 SMA로 변경

        val currentPrice = priceData[endDate] ?: throw Exception("현재 가격 데이터를 가져올 수 없습니다.")
        val currentRate = usUnemploymentRate.entries
            .filter { it.key <= endDate }
            .maxByOrNull { it.key }
            ?.value ?: throw Exception("현재 실업률을 가져올 수 없습니다.")

        // 전략 로직 적용
        return if (currentPrice.toDouble() < sma200 && currentRate < rate200) {
            result += SelectedAsset("SHY", null, 0.25)
            result
        } else {
            result += SelectedAsset("QQQ", null, 0.25)
            result
        }
    }

    // 월별 데이터로 SMA를 계산하는 함수
    private fun calculateMonthlySMA(data: Map<LocalDate, Double>, period: Int): Double {
        val sortedData = data.entries.sortedByDescending { it.key }.take(period)
        return if (sortedData.isNotEmpty()) {
            sortedData.sumOf { it.value } / sortedData.size
        } else {
            throw Exception("SMA를 계산할 데이터가 부족합니다.")
        }
    }
}