package com.spring.inventory.jwt

import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import javax.crypto.SecretKey

@Configuration
class JwtSecret: SecretKey {
    @Autowired
    lateinit var jwtConfig: JwtConfig

    @Bean
    fun encryptSecret(): SecretKey {
        return Keys.hmacShaKeyFor(encoded)
    }

    override fun getAlgorithm(): String {
        TODO("Not yet implemented")
    }

    override fun getFormat(): String {
        TODO("Not yet implemented")
    }

    override fun getEncoded(): ByteArray? {
        return jwtConfig.secret?.toByteArray()
    }
}
