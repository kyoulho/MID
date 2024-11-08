package com.kyoulho.mid.strategy.entity

import com.kyoulho.mid.const.RebalanceFrequency
import jakarta.persistence.*

@Entity
data class Strategy(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    val id: String = "",

    var name: String,

    @Enumerated(EnumType.STRING)
    var rebalanceFrequency: RebalanceFrequency,
) {
}