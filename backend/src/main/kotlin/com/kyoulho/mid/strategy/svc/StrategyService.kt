package com.kyoulho.mid.strategy.svc

import com.kyoulho.mid.exception.MIDException
import com.kyoulho.mid.strategy.dto.CreateStrategyDTO
import com.kyoulho.mid.strategy.dto.GetStrategyDTO
import com.kyoulho.mid.strategy.dto.UpdateStrategyDTO
import com.kyoulho.mid.strategy.dto.toDTO
import com.kyoulho.mid.strategy.entity.Strategy
import com.kyoulho.mid.strategy.repo.StrategyRepository
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class StrategyService(
    private val strategyRepository: StrategyRepository
) {
    @Transactional(readOnly = true)
    fun getStrategies(): List<GetStrategyDTO> {
        return strategyRepository.findAll().map { it.toDTO() }
    }

    @Transactional(readOnly = true)
    fun getStrategy(id: String): GetStrategyDTO {
        return strategyRepository.findById(id)
            .orElseThrow { throw MIDException(HttpStatus.BAD_REQUEST, "전략을 찾을 수 없습니다.") }
            .toDTO()
    }

    fun createStrategy(dto: CreateStrategyDTO): GetStrategyDTO {
        return Strategy(
            name = dto.name,
            rebalanceFrequency = dto.rebalanceFrequency,
        ).apply {
            strategyRepository.save(this)
        }.toDTO()
    }


    fun updateStrategy(id: String, dto: UpdateStrategyDTO): GetStrategyDTO {
        return strategyRepository.findById(id)
            .orElseThrow { throw MIDException(HttpStatus.BAD_REQUEST, "전략을 찾을 수 없습니다.") }
            .apply {
                name = dto.name
                rebalanceFrequency = dto.rebalanceFrequency
            }.toDTO()
    }

    fun deleteStrategy(id: String) {
        strategyRepository.deleteById(id)
    }
}