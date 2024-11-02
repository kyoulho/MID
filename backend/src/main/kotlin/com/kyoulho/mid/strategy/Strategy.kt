package com.kyoulho.mid.strategy

import com.kyoulho.mid.const.RebalanceFrequency
import com.kyoulho.mid.const.StrategyType
import com.kyoulho.mid.const.Ticker

// 투자 전략의 추상 클래스 - 구체적인 전략 클래스들이 상속하여 사용
abstract class InvestmentStrategy {
    abstract val alias: String
    abstract val name: String
    abstract val type: StrategyType
    abstract val rebalanceFrequency: RebalanceFrequency
    abstract val aggressiveAssets: Set<AggressiveAsset>    // 공격형 자산
    abstract val defensiveAssets: Set<DefensiveAsset>      // 방어형 자산
    abstract val canaryAssets: Set<CanaryAsset>            // 카나리아 자산
    abstract fun executeStrategy(): () -> Set<SelectedAsset>      // 전략 실행 메서드 - 최종 자산 선정
}

// 공격형 자산 클래스
data class AggressiveAsset(
    val primaryAssetTicker: Ticker,              // 기본 자산 티커
    val alternativeAssetTicker: Ticker? = null,  // 대체 자산 티커
    val momentumScoreMethod: MomentumScoreType   // 모멘텀 점수 계산 방식
)

// 방어형 자산 클래스 (안전 자산)
data class DefensiveAsset(
    val primaryAssetTicker: Ticker,              // 기본 방어형 자산 티커
    val alternativeAssetTicker: Ticker? = null,  // 대체 방어형 자산 티커
    val momentumScoreMethod: MomentumScoreType? = null // 모멘텀 점수 계산 방식 (선택 사항)
)

// 카나리아 자산 클래스 (위험 신호 탐지용 자산)
// TODO
data class CanaryAsset(
    val assetTicker: Ticker,                     // 카나리아 자산 티커
    val threshold: Double,                       // 위험 신호 감지 임계값
    val sensitivity: Double                      // 위험 민감도 (시장 위험 신호 감지에 대한 반응 정도)
)

// 최종 선택된 자산 클래스
data class SelectedAsset(
    val selectedAssetTicker: Ticker,             // 선택된 자산 티커 (기본 자산 또는 대체 자산)
    val backupAssetTicker: Ticker? = null,       // 대체 자산 티커
    val allocationRatio: Int                     // 전체 포트폴리오에서 이 자산의 비중 (0~100)
)