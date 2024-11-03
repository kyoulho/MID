package com.kyoulho.mid.strategy.todo

import yahoofinance.YahooFinance
import java.time.LocalDate
import java.time.ZoneId
import java.util.*

class YahooDataProvider : IPriceDataProvider {

    override fun getHistoricalPrices(ticker: String, startDate: LocalDate, endDate: LocalDate): Map<LocalDate, Long> {
        val yahooTicker = YahooFinance.get(ticker)
        val from = Calendar.getInstance()
        from.time = Date.from(startDate.atStartOfDay(ZoneId.systemDefault()).toInstant())
        val to = Calendar.getInstance()
        to.time = Date.from(endDate.atStartOfDay(ZoneId.systemDefault()).toInstant())

        val history = yahooTicker.getHistory(from, to, yahoofinance.histquotes.Interval.DAILY)

        val prices = history.associate {
            val date = it.date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
            date to it.close.toLong()
        }

        return prices
    }

    override fun fetchUSUnemploymentRate(startDate: LocalDate, endDate: LocalDate): Map<LocalDate, Double> {
        TODO("Not yet implemented")
    }
}