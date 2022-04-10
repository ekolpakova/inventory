package com.spring.inventory.jwt

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@ConfigurationProperties(prefix = "application.jwt")
class JwtConfig {
    var secret: String? = null
}
