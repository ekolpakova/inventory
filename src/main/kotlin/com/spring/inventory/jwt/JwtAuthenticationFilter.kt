package com.spring.inventory.jwt

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.transaction.annotation.Transactional
import java.util.*
import javax.crypto.SecretKey
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

open class JwtAuthenticationFilter(private var customAuthenticationManager: AuthenticationManager, secret: SecretKey, private val jwtUtil: JwtUtil): UsernamePasswordAuthenticationFilter() {

    var authManager: AuthenticationManager? = null

    init {
        authManager = customAuthenticationManager
    }

    //AuthenticationManager checks if principal is valid
    @Transactional
    override fun attemptAuthentication(request: HttpServletRequest?, response: HttpServletResponse?): Authentication {
        val username = request?.getParameter("username")
        val password = request?.getParameter("password")
        val authentication = UsernamePasswordAuthenticationToken(username, password)
        return customAuthenticationManager.authenticate(authentication)
    }

    override fun successfulAuthentication(
        request: HttpServletRequest?,
        response: HttpServletResponse?,
        chain: FilterChain?,
        authentication: Authentication?
    ) {
        val accessToken = jwtUtil.generateAccessToken(authentication!!.name, authentication.authorities)
        val refreshToken = jwtUtil.generateRefreshToken(authentication.name, authentication.authorities)

        val tokens: HashMap<String, String> = HashMap()
        tokens["access_token"] = accessToken
        tokens["refresh_token"] = refreshToken

        response?.contentType = MediaType.APPLICATION_JSON_VALUE
        ObjectMapper().writeValue(response?.outputStream, tokens)
    }

    override fun unsuccessfulAuthentication(
        request: HttpServletRequest?,
        response: HttpServletResponse?,
        exception: AuthenticationException?
    ) {
        response?.status = HttpStatus.UNAUTHORIZED.value()
        val data: HashMap<String, Any> = HashMap()
        data["timestamp"] = Calendar.getInstance().time
        data["exception"] = exception?.message.toString()
        ObjectMapper().writeValue(response?.outputStream, data)
    }
}
