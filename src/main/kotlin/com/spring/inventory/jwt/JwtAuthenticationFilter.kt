package com.spring.inventory.jwt

import com.fasterxml.jackson.databind.ObjectMapper
import com.spring.inventory.services.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.AuthenticationException
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.RequestBody
import java.util.*
import java.util.stream.Collectors
import javax.crypto.SecretKey
import javax.servlet.FilterChain
import javax.servlet.http.Cookie
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@CrossOrigin(origins = ["http://localhost:8080", "http://localhost:3000", "https://inventory-react.netlify.app"])
open class JwtAuthenticationFilter(private var customAuthenticationManager: AuthenticationManager, secret: SecretKey, private val jwtUtil: JwtUtil, private val userService: UserService): UsernamePasswordAuthenticationFilter() {

    var authManager: AuthenticationManager? = null

    init {
        authManager = customAuthenticationManager
    }

    @Transactional
    @org.springframework.lang.Nullable
    override fun attemptAuthentication(@RequestBody request: HttpServletRequest?, response: HttpServletResponse?): Authentication {
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

        /*val authorities = authentication.authorities as List<Map<String, String>>

        val simpleGrantedAuthorities = authorities.stream()
            .map { a -> SimpleGrantedAuthority(a["authority"]) }
            .collect(Collectors.toSet())

        val roles = simpleGrantedAuthorities.map { it.authority }*/

        val user = userService?.getUserByUsername(authentication.name)
        val roles = user?.roles as List<String>
        val tokens: HashMap<String, Any> = HashMap()

        tokens["access_token"] = accessToken
        tokens["refresh_token"] = refreshToken
        tokens["roles"] = mutableListOf(roles)
        tokens["id"] = user.id.toString()
        tokens["username"] = user.username.toString()

        val refreshCookie = Cookie("refresh_token", refreshToken)
        refreshCookie.maxAge = 604800
        refreshCookie.isHttpOnly = true /* can handle by js - false */
        refreshCookie.secure = true /* https only - false */
        refreshCookie.path = "/"

        response?.addCookie(refreshCookie)
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
        data["exception"] = /*exception?.message.toString() */ exception?.stackTrace.toString()

        ObjectMapper().writeValue(response?.outputStream, data)
    }
}
