package com.kyoulho.mid.auth.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.NestedConfigurationProperty
import org.springframework.stereotype.Component

@Component
@ConfigurationProperties(prefix = "security")
data class SecurityProperties(
    var permitAll: List<String> = listOf(),

    @NestedConfigurationProperty
    var jwt: JwtProperties = JwtProperties()
) {
    data class JwtProperties(
        var secret: String = "",
        var expirationHour: Long = 0
    )
}