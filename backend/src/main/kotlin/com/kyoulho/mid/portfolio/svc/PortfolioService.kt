package com.kyoulho.mid.portfolio.svc

import com.kyoulho.mid.exception.MIDException
import com.kyoulho.mid.portfolio.dto.*
import com.kyoulho.mid.portfolio.entity.Portfolio
import com.kyoulho.mid.portfolio.entity.PortfolioAsset
import com.kyoulho.mid.portfolio.repo.PortfolioAssetRepository
import com.kyoulho.mid.portfolio.repo.PortfolioRepository
import com.kyoulho.mid.strategy.repo.StrategyRepository
import com.kyoulho.mid.user.repo.UserRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class PortfolioService(
    private val portfolioRepository: PortfolioRepository,
    private val portfolioAssetRepository: PortfolioAssetRepository,
    private val strategyRepository: StrategyRepository,
    private val userRepository: UserRepository,
) {
    fun createPortfolio(userId: String, dto: CreatePortfolioDTO): GetPortfolioDTO {
        val user = userRepository.findByIdOrNull(userId)
            ?: throw MIDException(HttpStatus.UNAUTHORIZED, "존재 하지 않는 유저 아이디 $userId")

        val strategy = dto.strategyId?.let {
            strategyRepository.findByIdOrNull(dto.strategyId)
                ?: throw MIDException(HttpStatus.BAD_REQUEST, "존재 하지 않는 전략 아이디 ${dto.strategyId}")
        }

        val portfolio = Portfolio(
            strategy = strategy,
            description = dto.description,
            user = user
        )

        dto.assets.forEach { assetDto ->
            val portfolioAsset = PortfolioAsset(
                ticker = assetDto.ticker,
                targetRatio = assetDto.targetRatio,
                portfolio = portfolio
            )
            portfolio.assets.add(portfolioAsset)
        }

        return portfolioRepository.save(portfolio).toDTO()
    }

    @Transactional(readOnly = true)
    fun getPortfolio(userId: String): List<GetPortfolioDTO> {
        return portfolioRepository.findByUser_Id(userId)
            .map { it.toDTO() }
    }

    @Transactional(readOnly = true)
    fun getPortfolioById(userId: String, portfolioId: String): GetPortfolioDTO {
        return portfolioRepository.findByUser_IdAndId(userId, portfolioId)?.toDTO()
            ?: throw MIDException(HttpStatus.BAD_REQUEST, "존재 하지 않는 포트폴리오 아이디 $portfolioId")
    }

    fun updatePortfolio(userId: String, portfolioId: String, dto: UpdatePortfolioDTO): GetPortfolioDTO {
        val (strategyId, description) = dto

        val portfolio = portfolioRepository.findByUser_IdAndId(userId, portfolioId)
            ?: throw MIDException(HttpStatus.BAD_REQUEST, "존재 하지 않는 포트폴리오 아이디 $portfolioId")

        if (strategyId != null && portfolio.strategy?.id != strategyId) {
            val strategy = strategyRepository.findByIdOrNull(strategyId)
                ?: throw MIDException(HttpStatus.BAD_REQUEST, "존재 하지 않는 전략 아이디 $strategyId")
            portfolio.strategy = strategy
        }

        portfolio.description = description

        return portfolioRepository.save(portfolio).toDTO()
    }

    fun updatePortfolioAsset(
        userId: String,
        portfolioId: String,
        portfolioAssetId: String,
        dto: UpdatePortfolioAssetDTO
    ): GetPortfolioAssetDTO {
        val portfolioAsset = portfolioAssetRepository.findByIdAndPortfolio_IdAndPortfolio_User_Id(
            portfolioAssetId,
            portfolioId,
            userId
        ) ?: throw MIDException(HttpStatus.BAD_REQUEST, "존재하지 않는 포트폴리오 자산 아이디 $portfolioAssetId")

        portfolioAsset.ticker = dto.ticker
        portfolioAsset.targetRatio = dto.targetRatio

        return portfolioAsset.toDTO()
    }

    fun deletePortfolio(userId: String, portfolioId: String) {
        portfolioRepository.findByUser_IdAndId(userId, portfolioId)?.let {
            portfolioRepository.delete(it)
        } ?: throw MIDException(HttpStatus.BAD_REQUEST, "존재 하지 않는 포트폴리오 아이디 $portfolioId")
    }

    fun deletePortfolioAsset(userId: String, portfolioId: String, portfolioAssetId: String) {
        val portfolioAsset = portfolioAssetRepository.findByIdAndPortfolio_IdAndPortfolio_User_Id(
            portfolioAssetId,
            portfolioId,
            userId
        ) ?: throw MIDException(HttpStatus.BAD_REQUEST, "존재하지 않는 포트폴리오 자산 아이디 $portfolioAssetId")

        portfolioAssetRepository.delete(portfolioAsset)
    }

    fun createAssetTradingRecord(
        userId: String,
        portfolioId: String,
        assetId: String,
        dto: CreateAssetTradingRecordDTO
    ): GetAssetTradingRecordDTO {
        TODO("Not yet implemented")
    }

    fun updateAssetTradingRecord(
        userId: String,
        portfolioId: String,
        assetId: String,
        tradingRecordId: String,
        dto: CreateAssetTradingRecordDTO
    ): GetAssetTradingRecordDTO {
        TODO("Not yet implemented")
    }

    fun deleteAssetTradingRecord(userId: String, portfolioId: String, assetId: String, tradingRecordId: String) {
        TODO("Not yet implemented")
    }

    fun createAssetDividendRecord(
        id: String,
        portfolioId: String,
        assetId: String,
        dto: CreateAssetDividendRecordDTO
    ): GetAssetDividendRecordDTO {
        TODO("Not yet implemented")
    }

    fun updateAssetDividendRecord(
        id: String,
        portfolioId: String,
        assetId: String,
        dividendRecordId: String,
        dto: UpdateAssetDividendRecordDTO
    ): GetAssetDividendRecordDTO {
        TODO("Not yet implemented")
    }

    fun deleteAssetDividendRecord(id: String, portfolioId: String, assetId: String, dividendRecordId: String) {
        TODO("Not yet implemented")
    }


}
