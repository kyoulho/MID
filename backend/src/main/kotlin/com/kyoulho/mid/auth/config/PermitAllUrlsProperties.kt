package com.kyoulho.mid.auth.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

/**
 * YAML 파일에서 허용된 URL 목록을 주입받는 프로퍼티 클래스
 */
@Component
@ConfigurationProperties(prefix = "spring.security")
class PermitAllUrlsProperties {
    /**
     * 인증 없이 접근할 수 있는 URL 목록
     */
    lateinit var permitAllUrls: List<String>
}
