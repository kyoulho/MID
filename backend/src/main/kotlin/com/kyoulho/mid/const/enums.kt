package com.kyoulho.mid.const

// 계좌의 종류
enum class AccountType(val fields: List<AccountField>) {
    ISA(listOf(AccountField.OPEN_DATE)),
}

// 계좌의 동적 필드
enum class AccountField(val dataType: AccountFieldDataType) {
    OPEN_DATE(AccountFieldDataType.DATE),
    HOLDER_NAME(AccountFieldDataType.TEXT),
    OVERDRAFT_LIMIT(AccountFieldDataType.NUMBER),
    CREDIT_LIMIT(AccountFieldDataType.NUMBER)
}

// 계좌의 동적 필드 타입
enum class AccountFieldDataType {
    NUMBER,
    DATE,
    TEXT
}

// 거래 종류
enum class TradingType {
    BUY,
    SELL
}

// 리밸런싱 주기
enum class RebalanceFrequency {
    MONTHLY,
    ANNUAL,
    QUARTERLY,
    WEEKLY,
    DAILY,
}

// 전략 종류
enum class StrategyType(
    fullName: String
) {
    SAA("Static Asset Allocation"),
    DAA("Dynamic Asset Allocation"),
    MOM("Momentum")
}