package com.kyoulho.mid.portfolio.entity

import jakarta.persistence.*

@Entity
data class PortfolioAsset(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    val id: String = "",

    @Column(nullable = false)
    var ticker: String,

    @Column(nullable = false)
    var targetRatio: Double,

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "portfolio_id", nullable = false)
    val portfolio: Portfolio,

    @OneToMany(mappedBy = "portfolioAsset", cascade = [CascadeType.ALL], orphanRemoval = true, fetch = FetchType.LAZY)
    val records: List<AssetTradingRecord> = mutableListOf(),

    @OneToMany(mappedBy = "portfolioAsset", cascade = [CascadeType.ALL], orphanRemoval = true, fetch = FetchType.LAZY)
    val dividends: List<AssetDividendRecord> = mutableListOf()
)