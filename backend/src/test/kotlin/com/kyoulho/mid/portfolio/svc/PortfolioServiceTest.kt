package com.kyoulho.mid.portfolio.svc

import com.kyoulho.mid.account.entity.Account
import com.kyoulho.mid.account.repo.AccountRepository
import com.kyoulho.mid.const.AccountType
import com.kyoulho.mid.const.RebalanceFrequency
import com.kyoulho.mid.const.TradingType
import com.kyoulho.mid.const.UserRole
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
import com.kyoulho.mid.strategy.entity.Strategy
import com.kyoulho.mid.strategy.repo.StrategyRepository
import com.kyoulho.mid.user.entity.MidUser
import com.kyoulho.mid.user.repo.UserRepository
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.repository.findByIdOrNull
import org.springframework.http.HttpStatus
import org.springframework.transaction.annotation.Transactional
import java.math.BigDecimal
import java.time.LocalDate

@SpringBootTest
@Transactional
class PortfolioServiceTest @Autowired constructor(
    private val portfolioService: PortfolioService,
    private val userRepository: UserRepository,
    private val strategyRepository: StrategyRepository,
    private val portfolioRepository: PortfolioRepository,
    private val portfolioAssetRepository: PortfolioAssetRepository,
    private val portfolioAssetTradingRecordRepository: PortfolioAssetTradingRecordRepository,
    private val portfolioAssetDividendRecordRepository: PortfolioAssetDividendRecordRepository,
    private val accountRepository: AccountRepository
) {

    @Test
    fun `포트폴리오 생성 성공`() {
        val user = createUser()
        val strategy = createStrategy()
        val portfolioDto = CreatePortfolioDTO(
            strategyId = strategy.id,
            assets = listOf(CreatePortfolioAssetDTO("AAPL", 50.0)),
            description = "테스트 포트폴리오"
        )

        val createdPortfolio = portfolioService.createPortfolio(user.id, portfolioDto)

        val portfolioInDb = portfolioRepository.findByIdOrNull(createdPortfolio.id)
        assertNotNull(portfolioInDb)
        assertEquals("테스트 포트폴리오", portfolioInDb?.description)
        assertEquals(1, portfolioInDb?.assets?.size)
        assertEquals("AAPL", portfolioInDb?.assets?.first()?.ticker)
    }

    @Test
    fun `존재하지 않는 유저로 포트폴리오 생성 시 예외 발생`() {
        val portfolioDto = CreatePortfolioDTO(strategyId = null, assets = listOf(), description = "테스트 포트폴리오")

        val exception = assertThrows<MIDException> {
            portfolioService.createPortfolio("invalidUserId", portfolioDto)
        }

        assertEquals(HttpStatus.UNAUTHORIZED, exception.status)
    }

    @Test
    fun `포트폴리오 설명 업데이트 성공`() {
        val user = createUser()
        val strategy = createStrategy()
        val portfolioDto = CreatePortfolioDTO(strategyId = strategy.id, assets = listOf(), description = "이전 설명")
        val createdPortfolio = portfolioService.createPortfolio(user.id, portfolioDto)

        val updatedDto = UpdatePortfolioDTO(strategyId = strategy.id, description = "새로운 설명")
        val updatedPortfolio = portfolioService.updatePortfolio(user.id, createdPortfolio.id, updatedDto)

        assertEquals("새로운 설명", updatedPortfolio.description)
    }

    @Test
    fun `포트폴리오 자산 업데이트 성공`() {
        val user = createUser()
        val portfolio = createPortfolio(user)
        val portfolioAsset = createPortfolioAsset(portfolio, "AAPL", 50.0)

        val updateDto = UpdatePortfolioAssetDTO(ticker = "GOOGL", targetRatio = 75.0)
        val updatedAsset = portfolioService.updatePortfolioAsset(user.id, portfolio.id, portfolioAsset.id, updateDto)

        assertEquals("GOOGL", updatedAsset.ticker)
        assertEquals(75.0, updatedAsset.targetRatio)
    }

    @Test
    fun `포트폴리오 삭제 성공`() {
        val user = createUser()
        val portfolio = createPortfolio(user)

        portfolioService.deletePortfolio(user.id, portfolio.id)

        assertNull(portfolioRepository.findByIdOrNull(portfolio.id))
    }

    @Test
    fun `자산 거래 기록 생성 성공`() {
        val user = createUser()
        val account = createAccount(user)
        val portfolio = createPortfolio(user)
        val portfolioAsset = createPortfolioAsset(portfolio, "AAPL", 50.0)

        val tradingRecordDto = CreateAssetTradingRecordDTO(
            accountId = account.id,
            tradingType = TradingType.BUY,
            quantity = 10,
            price = 150,
            tradingDate = LocalDate.of(2024, 11, 8)
        )

        val tradingRecord =
            portfolioService.createAssetTradingRecord(user.id, portfolio.id, portfolioAsset.id, tradingRecordDto)

        assertEquals(TradingType.BUY, tradingRecord.tradingType)
        assertEquals(10, tradingRecord.quantity)
        assertEquals(150, tradingRecord.price)
    }

    @Test
    fun `자산 거래 업데이트 성공`() {
        val user = createUser()
        val account = createAccount(user)
        val portfolio = createPortfolio(user)
        val portfolioAsset = createPortfolioAsset(portfolio, "AAPL", 50.0)
        val tradingRecord = createTradingRecord(account, portfolioAsset)

        val updateDto = UpdateAssetTradingRecordDTO(
            accountId = account.id,
            tradingType = TradingType.SELL,
            quantity = 20,
            price = 200,
            tradingDate = LocalDate.of(2024, 11, 9)
        )

        val updatedRecord = portfolioService.updateAssetTradingRecord(
            user.id,
            portfolio.id,
            portfolioAsset.id,
            tradingRecord.id,
            updateDto
        )

        assertEquals(TradingType.SELL, updatedRecord.tradingType)
        assertEquals(20, updatedRecord.quantity)
        assertEquals(200, updatedRecord.price)
        assertEquals(LocalDate.of(2024, 11, 9), updatedRecord.tradingDate)
    }

    @Test
    fun `자산 배당 생성 성공`() {
        val user = createUser()
        val account = createAccount(user)
        val portfolio = createPortfolio(user)
        val portfolioAsset = createPortfolioAsset(portfolio, "AAPL", 50.0)

        val dividendDto = CreateAssetDividendRecordDTO(
            accountId = account.id,
            amount = 500,
            dividendDate = LocalDate.of(2024, 12, 1)
        )

        val createdDividend =
            portfolioService.createAssetDividendRecord(user.id, portfolio.id, portfolioAsset.id, dividendDto)

        assertEquals(500, createdDividend.amount)
        assertEquals(LocalDate.of(2024, 12, 1), createdDividend.dividendDate)
    }

    @Test
    fun `자산 배당 업데이트 성공`() {
        val user = createUser()
        val account = createAccount(user)
        val portfolio = createPortfolio(user)
        val portfolioAsset = createPortfolioAsset(portfolio, "AAPL", 50.0)
        val dividendRecord = createDividendRecord(account, portfolioAsset)

        val updateDto = UpdateAssetDividendRecordDTO(
            accountId = account.id,
            amount = 700,
            dividendDate = LocalDate.of(2024, 12, 15)
        )

        val updatedDividend = portfolioService.updateAssetDividendRecord(
            user.id,
            portfolio.id,
            portfolioAsset.id,
            dividendRecord.id,
            updateDto
        )

        assertEquals(700, updatedDividend.amount)
        assertEquals(LocalDate.of(2024, 12, 15), updatedDividend.dividendDate)
    }

    // Helper methods to create and persist necessary entities in the database
    private fun createUser(): MidUser {
        val user = MidUser(
            id = "testUserId",
            email = "test@test.com",
            name = "Test User",
            hashedPassword = "hashedPassword",
            phoneNumber = "phoneNumber",
            role = UserRole.ADMIN
        )
        return userRepository.save(user)
    }

    private fun createStrategy(): Strategy {
        val strategy = Strategy(id = "testStrategyId", name = "Test Strategy", RebalanceFrequency.ANNUAL)
        return strategyRepository.save(strategy)
    }

    private fun createPortfolio(user: MidUser): Portfolio {
        val portfolio = Portfolio(
            user = user,
            description = "Sample Portfolio",
            strategy = createStrategy()
        )
        return portfolioRepository.save(portfolio)
    }

    private fun createPortfolioAsset(portfolio: Portfolio, ticker: String, targetRatio: Double): PortfolioAsset {
        val portfolioAsset = PortfolioAsset(
            ticker = ticker,
            targetRatio = targetRatio,
            portfolio = portfolio
        )
        return portfolioAssetRepository.save(portfolioAsset)
    }

    private fun createAccount(user: MidUser): Account {
        val account =
            Account(
                id = "testAccountId",
                user = user,
                name = "Test Account",
                issuer = "issuer",
                number = "number",
                accountType = AccountType.ISA,
                interestRate = BigDecimal(0.03)
            )
        return accountRepository.save(account)
    }

    private fun createTradingRecord(account: Account, portfolioAsset: PortfolioAsset): PortfolioAssetTradingRecord {
        val tradingRecord = PortfolioAssetTradingRecord(
            account = account,
            portfolioAsset = portfolioAsset,
            tradingType = TradingType.BUY,
            quantity = 10,
            price = 150,
            tradingDate = LocalDate.of(2024, 11, 8)
        )
        return portfolioAssetTradingRecordRepository.save(tradingRecord)
    }

    private fun createDividendRecord(account: Account, portfolioAsset: PortfolioAsset): PortfolioAssetDividendRecord {
        val dividendRecord = PortfolioAssetDividendRecord(
            account = account,
            portfolioAsset = portfolioAsset,
            amount = 500,
            dividendDate = LocalDate.of(2024, 11, 8)
        )
        return portfolioAssetDividendRecordRepository.save(dividendRecord)
    }
}