package com.kyoulho.mid.const

enum class AccountTypeEnum(
    val fields: List<AccountFieldEnum>,
) {
    ISA(listOf(AccountFieldEnum.OPEN_DATE)),
}

enum class AccountFieldEnum(val fieldType: FieldType) {
    OPEN_DATE(FieldType.DATE),
    HOLDER_NAME(FieldType.TEXT),
    OVERDRAFT_LIMIT(FieldType.NUMBER),
    CREDIT_LIMIT(FieldType.NUMBER)
}

enum class FieldType {
    NUMBER,
    DATE,
    TEXT
}

enum class TradingType {
    BUY,
    SELL
}

typealias Ticker = String