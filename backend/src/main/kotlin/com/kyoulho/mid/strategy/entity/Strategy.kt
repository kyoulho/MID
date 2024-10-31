package com.kyoulho.mid.strategy.entity

import com.kyoulho.mid.const.RebalanceFrequency
import com.kyoulho.mid.const.StrategyType
import jakarta.persistence.*
import java.util.*

@Entity
data class Strategy(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    val id: String = "",

    @Column(length = 200, nullable = false)
    var name: String,

    @Column(length = 1000)
    var description: String?,

    @Lob
    var script: String? = null,

    @Column(length = 50, nullable = false)
    @Enumerated(EnumType.STRING)
    var rebalanceFrequency: RebalanceFrequency,

    @Column(length = 50, nullable = false)
    @Enumerated(EnumType.STRING)
    var type: StrategyType,
)
