package com.kyoulho.mid.portfolio.repo

import com.kyoulho.mid.portfolio.entity.Portfolio
import org.springframework.data.jpa.repository.JpaRepository

interface PortfolioRepository : JpaRepository<Portfolio, String> {
}