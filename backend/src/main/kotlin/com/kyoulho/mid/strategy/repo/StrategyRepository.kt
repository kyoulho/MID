package com.kyoulho.mid.strategy.repo

import com.kyoulho.mid.strategy.entity.Strategy
import org.springframework.data.jpa.repository.JpaRepository

interface StrategyRepository : JpaRepository<Strategy, String> {

}
