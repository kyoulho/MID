package com.kyoulho.mid.strategy.svc

import java.time.LocalDate

interface IDataService {
    // 지정된 일수 만큼 과거부터 현재까지의 종가 데이터를 가져온다.
    fun getClosingPriceDataForPastDays(daysBack: Int): Map<LocalDate, Long>
}