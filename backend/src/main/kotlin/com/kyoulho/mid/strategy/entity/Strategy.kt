package com.kyoulho.mid.strategy.entity

import com.kyoulho.mid.const.RebalanceFrequency
import com.kyoulho.mid.const.StrategyType
import com.kyoulho.mid.const.Ticker
import io.hypersistence.utils.hibernate.type.array.StringArrayType
import io.hypersistence.utils.hibernate.type.json.JsonBinaryType
import jakarta.persistence.*
import org.hibernate.annotations.Type

@Entity
data class Strategy(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    val id: String = "",

    var type: StrategyType,

    var alias: String,

    var name: String,

    @Enumerated(EnumType.STRING)
    var rebalanceFrequency: RebalanceFrequency,

    @Type(StringArrayType::class)
    @Column(
        name = "rules",
        columnDefinition = "text[]"
    )
    var rules: MutableList<String> = mutableListOf(),

    @Type(JsonBinaryType::class)
    @Column(
        name = "aggressive_assets",
        columnDefinition = "jsonb"
    )
    var aggressiveAssets: MutableSet<Asset> = mutableSetOf(),

    @Type(JsonBinaryType::class)
    @Column(
        name = "defensive_assets",
        columnDefinition = "jsonb"
    )
    var defensiveAssets: MutableSet<Asset> = mutableSetOf(),

    @Type(JsonBinaryType::class)
    @Column(
        name = "canary_assets",
        columnDefinition = "jsonb"
    )
    var canaryAssets: MutableSet<Asset> = mutableSetOf()
) {
}

data class Asset(
    val name: String,
    val ticker: Ticker
)