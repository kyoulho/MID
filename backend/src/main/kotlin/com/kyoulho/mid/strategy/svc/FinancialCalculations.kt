package com.kyoulho.mid.strategy.svc

import java.time.LocalDate

class FinancialCalculations : IFinancialCalculations {

    /**
     * 단순 이동 평균(SMA)을 계산하여 반환합니다.
     * @param prices 날짜별 종가 데이터 (LocalDate를 키로 하는 Map)
     * @param period 이동 평균을 계산할 기간 (일 단위)
     * @return 각 날짜별 SMA 값이 담긴 Map
     */
    // todo
    override fun calculateSimpleMovingAverage(prices: Map<LocalDate, Long>, period: Int): Map<LocalDate, Double> {
        val smaResults = mutableMapOf<LocalDate, Double>()
        val sortedDates = prices.keys.sorted()

        // 주어진 날짜에서 period 이전 날짜까지의 합을 사용하여 SMA 계산
        for (i in period - 1 until sortedDates.size) {
            val endDate = sortedDates[i]
            val sum = sortedDates.subList(i - period + 1, i + 1)
                .mapNotNull { prices[it]?.toDouble() }
                .sum()
            smaResults[endDate] = sum / period
        }
        return smaResults
    }

    /**
     * 모멘텀 스코어를 계산하여 반환합니다.
     * @param prices 날짜별 종가 데이터 (LocalDate를 키로 하는 Map)
     * @param periods 모멘텀을 계산할 기간들의 리스트 (월 단위)
     * @param weights 각 기간에 적용될 가중치 리스트
     * @return 각 날짜별 모멘텀 스코어가 담긴 Map
     */
    // todo
    override fun calculateMomentumScore(
        prices: Map<LocalDate, Long>,
        periods: List<Int>,
        weights: List<Double>
    ): Map<LocalDate, Double> {
        val momentumScores = mutableMapOf<LocalDate, Double>()
        val sortedDates = prices.keys.sorted()

        // 각 날짜별 모멘텀 스코어 계산
        for (date in sortedDates) {
            var momentumScore = 0.0

            // 각 기간과 가중치에 대해 계산
            for ((index, period) in periods.withIndex()) {
                val weight = weights.getOrElse(index) { 1.0 }  // 가중치가 제공되지 않은 경우 1로 처리
                val referenceDate = date.minusMonths(period.toLong())
                val referencePrice = prices[referenceDate]?.toDouble() ?: continue  // 기준 날짜의 가격이 없는 경우 건너뜀

                // 가중치 적용하여 모멘텀 스코어에 반영
                momentumScore += referencePrice * weight
            }
            momentumScores[date] = momentumScore
        }
        return momentumScores
    }
}