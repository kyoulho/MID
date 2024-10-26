package com.kyoulho.mid.portfolio

import com.kyoulho.mid.account.entity.Account
import jakarta.persistence.*
import java.util.*

@Entity
data class AssetTradingRecord(
    @Id
        @GeneratedValue(strategy = GenerationType.UUID)
        val id: String? = null,

    @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "account_id")
        val account: Account,

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
        @JoinColumn(name = "portfolio_asset_id", nullable = false)
        val portfolioAsset: PortfolioAsset,

    @Column(length = 50, nullable = false)
        val ticker: String,

    @Column(length = 50, nullable = false)
        @Enumerated(EnumType.STRING)
        val tradingType: TradingType,

    @Column(nullable = false)
        val quantity: Long,

    @Column(nullable = false)
        val price: Long,

    @Column(nullable = false)
        val tradingDate: Date
)
