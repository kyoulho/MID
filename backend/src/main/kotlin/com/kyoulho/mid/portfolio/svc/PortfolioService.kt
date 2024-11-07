package com.kyoulho.mid.portfolio.svc

import com.kyoulho.mid.portfolio.dto.CreatePortfolioDTO
import com.kyoulho.mid.portfolio.dto.GetPortfolioDTO
import com.kyoulho.mid.portfolio.dto.UpdatePortfolioDTO
import org.springframework.stereotype.Service

@Service
class PortfolioService {
    fun createPortfolio(userId: String, dto: CreatePortfolioDTO): GetPortfolioDTO {
        TODO("Not yet implemented")

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
