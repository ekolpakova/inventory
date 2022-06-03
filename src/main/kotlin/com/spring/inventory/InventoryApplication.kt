package com.spring.inventory

import com.spring.inventory.jwt.JwtConfig
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@SpringBootApplication
@EnableConfigurationProperties(JwtConfig::class)
@EnableGlobalMethodSecurity(prePostEnabled = true)
class InventoryApplication

fun main(args: Array<String>) {
	runApplication<InventoryApplication>(*args)
}

