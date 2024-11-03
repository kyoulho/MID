package com.kyoulho.mid.strategy.todo

import com.kyoulho.mid.exception.MIDException
import org.springframework.http.HttpStatus
import java.time.LocalDate

object Calculations {

    /**
     * 현재 날짜의 단순 이동 평균(SMA)을 계산하여 반환합니다.
     * @param prices 날짜별 종가 데이터 (LocalDate를 키로 하는 Map)
     * @param period 이동 평균을 계산할 기간 (일 단위)
     * @return 현재 날짜의 SMA 값
     * @throws MIDException 데이터가 부족하여 SMA를 계산할 수 없는 경우
     */
    @Throws(MIDException::class)
    fun calculateCurrentSimpleMovingAverage(prices: Map<LocalDate, Number>, period: Int): Double {
        val sortedDates = prices.keys.sorted()
        if (sortedDates.size < period) {
            throw MIDException(HttpStatus.INTERNAL_SERVER_ERROR, "SMA를 계산하기 위한 데이터가 충분하지 않습니다.")
        }
        val recentDates = sortedDates.takeLast(period)
        val sum = recentDates
            .mapNotNull { prices[it]?.toDouble() }
            .sum()
        return sum / period
    }

    /**
     * 현재 날짜의 모멘텀 스코어를 계산하여 반환합니다.
     * @param prices 날짜별 종가 데이터 (LocalDate를 키로 하는 Map)
     * @param periodsToWeights 기간(개월 수)과 해당 기간의 가중치를 나타내는 Map
     * @return 현재 날짜의 모멘텀 스코어 값
     * @throws MIDException 모멘텀 스코어를 계산할 수 없는 경우
     */
    @Throws(MIDException::class)
    fun calculateCurrentMomentumScore(
        prices: Map<LocalDate, Number>,
        periodsToWeights: Map<Int, Double>
    ): Double {
        val latestDate = prices.keys.maxOrNull()
            ?: throw MIDException(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "가격 데이터가 존재하지 않습니다."
            ) // 가장 최근 날짜가 없을 경우 예외 발생

        val latestPrice = prices[latestDate]?.toDouble()
            ?: throw MIDException(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "현재 날짜의 가격 데이터가 없습니다."
            ) // 현재 가격이 없을 경우 예외 발생

        var momentumScore = 0.0
        var totalWeight = 0.0

        // 기간과 가중치를 순회하면서 계산
        for ((period, weight) in periodsToWeights) {
            val referenceDate = latestDate.minusMonths(period.toLong())
            val referencePrice = prices[referenceDate]?.toDouble()
                ?: throw MIDException(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "모멘텀 스코어를 계산하기 위한 기준 날짜($referenceDate)의 가격 데이터가 없습니다."
                )

            // 모멘텀 계산: ((현재 가격 - 기준 가격) / 기준 가격) * 가중치
            val momentum = ((latestPrice - referencePrice) / referencePrice) * weight
            momentumScore += momentum
            totalWeight += weight

        }

        if (totalWeight == 0.0) {
            throw MIDException(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "가중치의 합이 0입니다."
            )
        }

        return momentumScore / totalWeight
    }
}