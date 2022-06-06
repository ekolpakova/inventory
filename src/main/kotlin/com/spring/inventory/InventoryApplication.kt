package com.spring.inventory

import com.spring.inventory.jwt.JwtConfig
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody


@SpringBootApplication
@EnableConfigurationProperties(JwtConfig::class)
@EnableGlobalMethodSecurity(prePostEnabled = true)
class InventoryApplication

@RequestMapping("/")
@ResponseBody
fun home(): String? {
    return "Hello World!"
}

fun main(args: Array<String>) {
	runApplication<InventoryApplication>(*args)
}

