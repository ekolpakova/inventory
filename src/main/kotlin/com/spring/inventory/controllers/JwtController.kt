package com.spring.inventory.controllers

import com.fasterxml.jackson.databind.ObjectMapper
import com.spring.inventory.entities.User
import com.spring.inventory.jwt.JwtUtil
import com.spring.inventory.payload.SignInRequest
import com.spring.inventory.payload.SignUpRequest
import com.spring.inventory.repositories.UserRepository
import com.spring.inventory.services.UserService
import io.jsonwebtoken.Jwts
import org.apache.tomcat.util.http.parser.Authorization
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpHeaders.AUTHORIZATION
import org.springframework.http.MediaType
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import java.util.*
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import kotlin.collections.HashMap

@Controller
@RequestMapping("/api/v1/public")
class JwtController {

    @Autowired
    lateinit var authenticationManager: AuthenticationManager

    @Autowired
    lateinit var passwordEncoder: PasswordEncoder

    @Autowired
    lateinit var userService: UserService

    @Autowired
    lateinit var jwtUtil: JwtUtil

    @GetMapping("signIn")
    fun signIn(@RequestBody signInRequest: SignInRequest, response: HttpServletResponse) {
        val authentication = authenticationManager.authenticate(
            UsernamePasswordAuthenticationToken(
                signInRequest.username,
                signInRequest.password
            )
        )
        SecurityContextHolder.getContext().authentication = authentication
        //
        //val refreshToken

        val accessToken = jwtUtil.generateAccessToken(authentication.name, authentication.authorities)
        val refreshToken = jwtUtil.generateRefreshToken(authentication.name, authentication.authorities)

        val tokens: HashMap<String, String> = HashMap()
        tokens["access_token"] = accessToken
        tokens["refresh_token"] = refreshToken

        response.contentType = MediaType.APPLICATION_JSON_VALUE
        ObjectMapper().writeValue(response.outputStream, tokens)
    }

    //get new access token by retrieving refresh token from header. RT is issued on success authentication once.
    @GetMapping("getNewAccessToken")
    fun getNewAccessToken(request: HttpServletRequest, response: HttpServletResponse, authentication: Authentication) {
        val authorizationHeader = request.getHeader(AUTHORIZATION)
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            try {
                val refreshToken = authorizationHeader.substring(7, authorizationHeader.length)

                /*val accessToken = Jwts.builder()
                    .setSubject(authentication.name)
                    .claim("authorities", authentication.authorities)
                    .setIssuedAt(Date())
                    //&more
                    .compact()*/

                val accessToken = jwtUtil.generateAccessToken(authentication.name, authentication.authorities)

                val tokens: HashMap<String, String> = HashMap()
                tokens["access_token"] = accessToken
                tokens["refresh_token"] = refreshToken

                response.contentType = MediaType.APPLICATION_JSON_VALUE
                ObjectMapper().writeValue(response.outputStream, tokens) //Jackson. Turns values into JSON => {"access_token":"xyz", "refresh_token":"zyx"}
            }
            catch (e: Exception) {

            }
        }
    }
    @PostMapping("signUp")
    fun signUp(@RequestBody signUpRequest: SignUpRequest, response: HttpServletResponse) {
        val user = User(
            signUpRequest.username!!,
            signUpRequest.password!!
        )
        user.password = passwordEncoder.encode(user.password)
        userService.saveUser(user)
        ObjectMapper().writeValue(response.outputStream, user)
    }
}
