package com.kyoulho.mid.portfolio.entity

import com.kyoulho.mid.account.entity.Account
import com.kyoulho.mid.const.TradingType
import jakarta.persistence.*
import java.time.LocalDate
import java.util.*

@Entity
data class PortfolioAssetTradingRecord(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    val id: String = "",

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "account_id")
    var account: Account,

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "portfolio_asset_id", nullable = false)
    val portfolioAsset: PortfolioAsset,

    @Column(length = 50, nullable = false)
    @Enumerated(EnumType.STRING)
    var tradingType: TradingType,

    @Column(nullable = false)
    var quantity: Long,

    @Column(nullable = false)
    var price: Long,

    @Column(nullable = false)
    var tradingDate: LocalDate
)
