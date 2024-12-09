package com.kyoulho.mid.auth.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

@Component
@ConfigurationProperties(prefix = "security")
data class SecurityProperties(
    var permitAll: List<String> = listOf(),
) {
}