package com.spring.inventory.jwt

import io.jsonwebtoken.Jwts
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.GrantedAuthority
import org.springframework.stereotype.Component
import java.time.LocalDate
import java.util.*
import javax.servlet.http.HttpServletRequest

@Component
class JwtUtil {
    @Autowired
    lateinit var jwtSecret: JwtSecret

    @Autowired
    lateinit var request: HttpServletRequest

    fun generateAccessToken(username: String, authorities: Collection<GrantedAuthority>): String {
        val accessToken = Jwts.builder()
            .setSubject(username)
            .claim("authorities", authorities)
            .setIssuer(request.requestURI)
            .setIssuedAt(Date())
            .setExpiration(Date(System.currentTimeMillis() + 15 * 60 * 1000))
            .signWith(jwtSecret.encryptSecret())
            .compact()
        return accessToken
    }

    fun generateRefreshToken(username: String, authorities: Collection<GrantedAuthority>): String {
        val refreshToken = Jwts.builder()
            .setSubject(username)
            .claim("authorities", authorities)
            .setIssuer(request.requestURI)
            .setIssuedAt(Date())
            .setExpiration(java.sql.Date.valueOf(LocalDate.now().plusYears(1)))
            .signWith(jwtSecret.encryptSecret())
            .compact()
        return refreshToken
    }

    fun getUsernameFromToken() {

    }

    fun getClaimsFromToken() {

    }
}
