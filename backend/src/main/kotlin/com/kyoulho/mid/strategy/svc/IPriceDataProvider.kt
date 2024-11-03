package com.kyoulho.mid.strategy.svc

import com.kyoulho.mid.const.Ticker
import java.time.LocalDate

interface IPriceDataProvider {
    fun getHistoricalPrices(ticker: Ticker, startDate: LocalDate, endDate: LocalDate): Map<LocalDate, Number>
    fun fetchUSUnemploymentRate(startDate: LocalDate, endDate: LocalDate): Map<LocalDate, Double>
}