package com.spring.inventory.jwt

import io.jsonwebtoken.Jwts
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.security.core.GrantedAuthority
import org.springframework.stereotype.Component
import org.springframework.web.context.request.NativeWebRequest
import org.springframework.web.context.request.RequestContextHolder
import org.springframework.web.context.request.RequestContextListener
import java.time.LocalDate
import java.util.*
import javax.servlet.http.HttpServletRequest


@Component
class JwtUtil {
    @Autowired
    lateinit var jwtSecret: JwtSecret

    @Autowired
    lateinit var request: HttpServletRequest

    @Bean
    fun requestContextListener(): RequestContextListener {
        return RequestContextListener()
    }

    fun getRemoteAddress(): String? {
        val attribs = RequestContextHolder.getRequestAttributes()
        if (attribs is NativeWebRequest) {
            val request = attribs.nativeRequest as HttpServletRequest
            return request.remoteAddr
        }
        return null
    }

    fun generateAccessToken(username: String, authorities: Collection<GrantedAuthority>): String {
        val roles = authorities.map { it.authority }
        val accessToken = Jwts.builder()
            .setSubject(username)
            .claim("authorities", authorities)
            .claim("roles", roles)
            .setIssuer(getRemoteAddress())
            .setIssuedAt(Date())
            .setExpiration(Date(System.currentTimeMillis() + 5 * 60 * 1000)) //15
            .signWith(jwtSecret.encryptSecret())
            .compact()
        return accessToken
    }

    fun generateRefreshToken(username: String, authorities: Collection<GrantedAuthority>): String {
        val roles = authorities.map { it.authority }
        val refreshToken = Jwts.builder()
            .setSubject(username)
            .claim("authorities", authorities)
            .claim("roles", roles)
            .setIssuer(getRemoteAddress())
            .setIssuedAt(Date())
            .setExpiration(Date(System.currentTimeMillis() + 1440 * 60 * 1000)) //15
            //.setExpiration(java.sql.Date.valueOf(LocalDate.now().plusYears(1)))
            .signWith(jwtSecret.encryptSecret())
            .compact()
        return refreshToken
    }

    fun getUsernameFromToken() {

    }

    fun getClaimsFromToken() {

    }
}
