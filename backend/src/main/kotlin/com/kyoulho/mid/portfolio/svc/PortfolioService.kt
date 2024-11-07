package com.kyoulho.mid.portfolio.svc

import com.kyoulho.mid.exception.MIDException
import com.kyoulho.mid.portfolio.dto.CreatePortfolioDTO
import com.kyoulho.mid.portfolio.dto.GetPortfolioDTO
import com.kyoulho.mid.portfolio.dto.UpdatePortfolioDTO
import com.kyoulho.mid.portfolio.dto.toDTO
import com.kyoulho.mid.portfolio.entity.Portfolio
import com.kyoulho.mid.portfolio.entity.PortfolioAsset
import com.kyoulho.mid.portfolio.repo.PortfolioRepository
import com.kyoulho.mid.strategy.entity.Strategy
import com.kyoulho.mid.strategy.repo.StrategyRepository
import com.kyoulho.mid.user.repo.UserRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.http.HttpStatus
import org.springframework.security.core.userdetails.User
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class PortfolioService(
    private val portfolioRepository: PortfolioRepository,
    private val strategyRepository: StrategyRepository,
    private val userRepository: UserRepository,
) {
    fun createPortfolio(userId: String, dto: CreatePortfolioDTO): GetPortfolioDTO {
        val user = userRepository.findByIdOrNull(userId)
            ?: throw MIDException(HttpStatus.UNAUTHORIZED,"존재 하지 않는 유저 아이디 $userId")

        val strategy = dto.strategyId?.let {
            strategyRepository.findByIdOrNull(dto.strategyId)
                ?: throw MIDException(HttpStatus.BAD_REQUEST, "존재 하지 않는 전략 아이디 ${dto.strategyId}")
        }

        val portfolio = Portfolio(
            strategy = strategy,
            description = dto.description,
            user = user
        )

        val assets = dto.assets.map {
            PortfolioAsset(
                intendedAsset = it.intendedAsset,
                targetRatio = it.targetRatio
            ).apply {
                portfolio.setAsset(this)
            }
        }
        return portfolioRepository.save(portfolio).toDTO()
    }

    fun getPortfolio(userId: String): List<GetPortfolioDTO> {
        TODO("Not yet implemented")
    }

    fun getPortfolioById(userId: String, portfolioId: String): GetPortfolioDTO {
        TODO("Not yet implemented")
    }

    fun updatePortfolio(userId: String, portfolioId: String, dto: UpdatePortfolioDTO): GetPortfolioDTO {
        TODO("Not yet implemented")
    }

    fun deletePortfolio(userId: String, portfolioId: String) {
        TODO("Not yet implemented")
    }

}
