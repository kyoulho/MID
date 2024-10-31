package com.kyoulho.mid.portfolio.entity

import com.kyoulho.mid.strategy.entity.Strategy
import com.kyoulho.mid.user.entity.MidUser
import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import java.time.LocalDateTime

@Entity
data class Portfolio(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    val id: String = "",

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "strategy_id", nullable = false)
    val strategy: Strategy,

    @OneToMany(mappedBy = "portfolio", cascade = [CascadeType.ALL], orphanRemoval = true, fetch = FetchType.EAGER)
    val portfolioAssets: List<PortfolioAsset> = mutableListOf(),

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    val createdAt: LocalDateTime? = null,

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    val user: MidUser
)
