package com.kyoulho.mid.strategy.svc

import java.time.LocalDate

interface IFinancialCalculations {
    /**
     * 주어진 기간 동안의 단순 이동평균(SMA)을 계산한다.
     * @param prices 날짜별 종가 데이터
     * @param period 계산에 사용될 기간 (일 단위)
     * @return 날짜별 계산된 SMA 값
     */
    fun calculateSimpleMovingAverage(prices: Map<LocalDate, Long>, period: Int): Map<LocalDate, Double>

    /**
     * 주어진 기간과 가중치에 따라 모멘텀 스코어를 계산한다.
     * @param prices 날짜별 종가 데이터
     * @param periods 계산에 사용될 기간들의 리스트
     * @param weights 각 기간에 적용될 가중치 (가중 모멘텀 계산에 사용, 단순 모멘텀의 경우 모든 가중치는 1)
     * @return 날짜별 계산된 모멘텀 스코어
     */
    fun calculateMomentumScore(
        prices: Map<LocalDate, Long>,
        periods: List<Int>,
        weights: List<Double>
    ): Map<LocalDate, Double>
}