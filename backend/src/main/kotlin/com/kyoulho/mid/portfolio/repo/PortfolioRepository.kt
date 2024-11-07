package com.kyoulho.mid.portfolio.repo

import com.kyoulho.mid.portfolio.entity.Portfolio
import com.kyoulho.mid.user.entity.MidUser
import org.springframework.data.jpa.repository.JpaRepository

interface PortfolioRepository : JpaRepository<Portfolio, String> {
    fun findByUser(user: MidUser): List<Portfolio>
    fun findByUser_IdAndId(user_id: String, id: String): Portfolio?
    fun findByUser_Id(userId: String): List<Portfolio>
}