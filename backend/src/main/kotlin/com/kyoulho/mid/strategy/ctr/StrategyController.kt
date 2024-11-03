package com.kyoulho.mid.strategy.ctr

import com.kyoulho.mid.strategy.dto.CreateStrategyDTO
import com.kyoulho.mid.strategy.dto.GetStrategyDTO
import com.kyoulho.mid.strategy.dto.UpdateStrategyDTO
import com.kyoulho.mid.strategy.svc.StrategyService
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*
import java.util.*

@Tag(name = "전략")
@SecurityRequirement(name = "Authorization")
@RestController
@RequestMapping("/api/strategies")
class StrategyController(
    private val strategyService: StrategyService
) {

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    fun getStrategies(): List<GetStrategyDTO> {
        return strategyService.getStrategies()
    }

    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    fun getStrategyById(
        @PathVariable id: String
    ): GetStrategyDTO {
        return strategyService.getStrategy(id)
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    fun createStrategy(@RequestBody strategyDTO: CreateStrategyDTO): GetStrategyDTO {
        return strategyService.createStrategy(strategyDTO)
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    fun updateStrategy(
        @PathVariable id: String,
        @RequestBody strategyDTO: UpdateStrategyDTO
    ): GetStrategyDTO {
        return strategyService.updateStrategy(id, strategyDTO)
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    fun deleteStrategy(
        @PathVariable id: String
    ) {
        strategyService.deleteStrategy(id)
    }

    @GetMapping("/{id}/exec")
    @PreAuthorize("isAuthenticated()")
    fun executeAlgorithm(
        @PathVariable id: String
    ): String {
        TODO()
    }
}