package com.kyoulho.mid.portfolio.svc

import com.kyoulho.mid.account.repo.AccountRepository
import com.kyoulho.mid.exception.MIDException
import com.kyoulho.mid.portfolio.dto.*
import com.kyoulho.mid.portfolio.entity.Portfolio
import com.kyoulho.mid.portfolio.entity.PortfolioAsset
import com.kyoulho.mid.portfolio.entity.PortfolioAssetDividendRecord
import com.kyoulho.mid.portfolio.entity.PortfolioAssetTradingRecord
import com.kyoulho.mid.portfolio.repo.PortfolioAssetDividendRecordRepository
import com.kyoulho.mid.portfolio.repo.PortfolioAssetRepository
import com.kyoulho.mid.portfolio.repo.PortfolioAssetTradingRecordRepository
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
    private val portfolioAssetTradingRecordRepository: PortfolioAssetTradingRecordRepository,
    private val portfolioAssetDividendRecordRepository: PortfolioAssetDividendRecordRepository,
    private val strategyRepository: StrategyRepository,
    private val userRepository: UserRepository,
    private val accountRepository: AccountRepository,
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
        portfolioAssetId: String,
        dto: CreateAssetTradingRecordDTO
    ): GetAssetTradingRecordDTO {
        val portfolioAsset = portfolioAssetRepository.findByIdAndPortfolio_IdAndPortfolio_User_Id(
            portfolioAssetId,
            portfolioId,
            userId
        ) ?: throw MIDException(HttpStatus.BAD_REQUEST, "존재하지 않는 포트폴리오 자산 아이디 $portfolioAssetId")

        val account = accountRepository.findByIdOrNull(dto.accountId) ?: throw MIDException(
            HttpStatus.BAD_REQUEST,
            "존재하지 않는 계좌 아이디 ${dto.accountId}"
        )

        val tradingRecord = PortfolioAssetTradingRecord(
            account = account,
            portfolioAsset = portfolioAsset,
            tradingType = dto.tradingType,
            quantity = dto.quantity,
            price = dto.price,
            tradingDate = dto.tradingDate
        )
        portfolioAsset.records.add(tradingRecord)
        portfolioAssetRepository.save(portfolioAsset)

        return tradingRecord.toDTO()
    }

    fun updateAssetTradingRecord(
        userId: String,
        portfolioId: String,
        portfolioAssetId: String,
        tradingRecordId: String,
        dto: CreateAssetTradingRecordDTO
    ): GetAssetTradingRecordDTO {
        val tradingRecord =
            portfolioAssetTradingRecordRepository
                .findByIdAndPortfolioAsset_IdAndPortfolioAsset_Portfolio_IdAndPortfolioAsset_Portfolio_User_Id(
                    tradingRecordId, portfolioAssetId, portfolioId, userId
                ) ?: throw MIDException(HttpStatus.BAD_REQUEST, "존재하지 않는 거래 기록 아이디 $tradingRecordId")

        tradingRecord.apply {
            if (dto.accountId != this.account.id) {
                account = accountRepository.findByIdOrNull(dto.accountId)
                    ?: throw MIDException(HttpStatus.BAD_REQUEST, "존재하지 않는 계좌 아이디 ${dto.accountId}")
            }
            this.tradingType = dto.tradingType
            this.quantity = dto.quantity
            this.price = dto.price
            this.tradingDate = dto.tradingDate
        }

        return portfolioAssetTradingRecordRepository.save(tradingRecord).toDTO()
    }

    fun deleteAssetTradingRecord(
        userId: String,
        portfolioId: String,
        portfolioAssetId: String,
        tradingRecordId: String
    ) {
        val tradingRecord =
            portfolioAssetTradingRecordRepository.findByIdAndPortfolioAsset_IdAndPortfolioAsset_Portfolio_IdAndPortfolioAsset_Portfolio_User_Id(
                tradingRecordId, portfolioAssetId, portfolioId, userId
            ) ?: throw MIDException(HttpStatus.BAD_REQUEST, "존재하지 않는 거래 기록 아이디 $tradingRecordId")

        portfolioAssetTradingRecordRepository.delete(tradingRecord)
    }

    fun createAssetDividendRecord(
        userId: String,
        portfolioId: String,
        portfolioAssetId: String,
        dto: CreateAssetDividendRecordDTO
    ): GetAssetDividendRecordDTO {
        val portfolioAsset = portfolioAssetRepository.findByIdAndPortfolio_IdAndPortfolio_User_Id(
            portfolioAssetId, portfolioId, userId
        ) ?: throw MIDException(HttpStatus.BAD_REQUEST, "존재하지 않는 포트폴리오 자산 아이디 $portfolioAssetId")

        val account = accountRepository.findByIdOrNull(dto.accountId) ?: throw MIDException(
            HttpStatus.BAD_REQUEST,
            "존재하지 않는 계좌 아이디 ${dto.accountId}"
        )

        val dividendRecord = PortfolioAssetDividendRecord(
            account = account,
            portfolioAsset = portfolioAsset,
            amount = dto.amount,
            dividendDate = dto.dividendDate
        )

        portfolioAssetDividendRecordRepository.save(dividendRecord)
        return dividendRecord.toDTO()
    }

    fun updateAssetDividendRecord(
        userId: String,
        portfolioId: String,
        portfolioAssetId: String,
        dividendRecordId: String,
        dto: UpdateAssetDividendRecordDTO
    ): GetAssetDividendRecordDTO {
        val dividendRecord =
            portfolioAssetDividendRecordRepository.findByIdAndPortfolioAsset_IdAndPortfolioAsset_Portfolio_IdAndPortfolioAsset_Portfolio_User_Id(
                dividendRecordId, portfolioAssetId, portfolioId, userId
            ) ?: throw MIDException(HttpStatus.BAD_REQUEST, "존재하지 않는 배당 기록 아이디 $dividendRecordId")

        dividendRecord.apply {
            if (dto.accountId != this.account.id) {
                this.account = accountRepository.findByIdOrNull(dto.accountId)
                    ?: throw MIDException(HttpStatus.BAD_REQUEST, "존재하지 않는 계좌 아이디 ${dto.accountId}")
            }
            this.amount = dto.amount
            this.dividendDate = dto.dividendDate
        }

        return portfolioAssetDividendRecordRepository.save(dividendRecord).toDTO()
    }

    fun deleteAssetDividendRecord(
        userId: String,
        portfolioId: String,
        portfolioAssetId: String,
        dividendRecordId: String
    ) {
        val dividendRecord =
            portfolioAssetDividendRecordRepository.findByIdAndPortfolioAsset_IdAndPortfolioAsset_Portfolio_IdAndPortfolioAsset_Portfolio_User_Id(
                dividendRecordId, portfolioAssetId, portfolioId, userId
            ) ?: throw MIDException(HttpStatus.BAD_REQUEST, "존재하지 않는 배당 기록 아이디 $dividendRecordId")

        portfolioAssetDividendRecordRepository.delete(dividendRecord)
    }


}
