package com.spring.inventory.controllers

import com.fasterxml.jackson.databind.ObjectMapper
import com.spring.inventory.entities.Role
import com.spring.inventory.entities.User
import com.spring.inventory.jwt.JwtUtil
import com.spring.inventory.payload.SignInRequest
import com.spring.inventory.payload.SignUpRequest
import com.spring.inventory.services.UserService
import io.jsonwebtoken.Jwts
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpHeaders.AUTHORIZATION
import org.springframework.http.MediaType
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import java.util.*
import java.util.stream.Collectors
import javax.crypto.SecretKey
import javax.servlet.http.Cookie
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import javax.validation.Valid
import kotlin.collections.HashMap


@RestController
@RequestMapping("/api/v1/public")
@CrossOrigin(origins = ["http://localhost:8080", "http://localhost:3000", "https://inventory-react.netlify.app"])
class JwtController(val jwtSecret: SecretKey) {
    @Autowired
    lateinit var authenticationManager: AuthenticationManager

    @Autowired
    lateinit var passwordEncoder: PasswordEncoder

    @Autowired
    lateinit var userService: UserService

    @Autowired
    lateinit var jwtUtil: JwtUtil

    @GetMapping("signIn") //RequestBody before
    fun signIn(@RequestParam signInRequest: SignInRequest, response: HttpServletResponse) {
        val authentication = authenticationManager.authenticate(
            UsernamePasswordAuthenticationToken(
                signInRequest.username,
                signInRequest.password
            )
        )
        SecurityContextHolder.getContext().authentication = authentication

        val accessToken = jwtUtil.generateAccessToken(authentication.name, authentication.authorities)
        val refreshToken = jwtUtil.generateRefreshToken(authentication.name, authentication.authorities)

        val refreshCookie = Cookie("refresh_token", refreshToken)
        refreshCookie.maxAge = 604800
        /*refreshCookie.isHttpOnly = false*/
        refreshCookie.isHttpOnly = true
        /* refreshCookie.secure = true */
        refreshCookie.secure = true
        refreshCookie.path = "/"

        val authorities = authentication.authorities as List<Map<String, String>>

        val simpleGrantedAuthorities = authorities.stream()
            .map { a -> SimpleGrantedAuthority(a["authority"]) }
            .collect(Collectors.toSet())

        val roles = simpleGrantedAuthorities.map { it.authority }
        val tokens: HashMap<String, Any> = HashMap()
        tokens["access_token"] = accessToken
        tokens["refresh_token"] = refreshToken
        tokens["roles"] = mutableListOf<List<String>>(roles)

        response.addCookie(refreshCookie)
        response.contentType = MediaType.APPLICATION_JSON_VALUE
        ObjectMapper().writeValue(response.outputStream, tokens)
    }

    @GetMapping("getAccessTokenExpirationDate")
    fun getAccessTokenExpirationDate(jwtSecret: SecretKey, access_token: String, request: HttpServletRequest, response: HttpServletResponse) {
        val claims = Jwts
            .parserBuilder()
            .setSigningKey(jwtSecret.encoded)
            .build()
            .parseClaimsJws(access_token)

        val body = claims.body
        val expiration_date = body.expiration
        ObjectMapper().writeValue(response.outputStream, expiration_date)
    }

    @GetMapping("getAccessTokenAuthorities")
    fun getAccessTokenSubject(@CookieValue(value = "refresh_token") refreshToken: String, request: HttpServletRequest, response: HttpServletResponse) {
        val claims = Jwts
            .parserBuilder()
            .setSigningKey(jwtSecret.encoded)
            .build()
            .parseClaimsJws(refreshToken)

        val body = claims.body

        val authorities = body["authorities"] as List<Map<String, String>>

        val simpleGrantedAuthorities = authorities.stream()
            .map { a -> SimpleGrantedAuthority(a["authority"]) }
            .collect(Collectors.toSet())

        val roles = simpleGrantedAuthorities.map { it.authority }

        ObjectMapper().writeValue(response.outputStream, roles)
    }

    //Jackson. Turns values into JSON => {"access_token":"xyz", "refresh_token":"zyx"}
    //get new access token by retrieving refresh token from header. RT is issued on success authentication once.
    @GetMapping("getNewAccessToken")
    fun getNewAccessToken(@CookieValue(value = "refresh_token") refreshToken: String, request: HttpServletRequest, response: HttpServletResponse) {
        /*var accessToken = "";
        val authorizationHeader = request.getHeader(AUTHORIZATION)
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            accessToken = authorizationHeader.substring(7, authorizationHeader.length)
        }*/
        val claims = Jwts
            .parserBuilder()
            .setSigningKey(jwtSecret.encoded)
            .build()
            .parseClaimsJws(refreshToken)

        val body = claims.body
        val username = body.subject

        //val user = userService.getUserByUsername(username)
        //val roles = user.roles as List<String>
        //val tokens: java.util.HashMap<String, Any> = java.util.HashMap()

        val user = userService?.getUserByUsername(username)
        val roles = user?.roles as List<String>

        val authorities = body["authorities"] as List<Map<String, String>>

        val simpleGrantedAuthorities = authorities.stream()
            .map { a -> SimpleGrantedAuthority(a["authority"]) }
            .collect(Collectors.toSet())

        val newAccessToken = jwtUtil.generateAccessToken(body.subject, simpleGrantedAuthorities)
        val tokens: HashMap<String, Any> = HashMap()
        //val roles = mutableListOf<Any>(newAccessToken)
        //roles.add(rolesList)
        tokens["access_token"] = newAccessToken
        tokens["roles"] = mutableListOf(roles)
        tokens["id"] = user.id.toString()
        tokens["username"] = user.username.toString()

        //token["roles"] = rolesList
        response.contentType = MediaType.APPLICATION_JSON_VALUE
        ObjectMapper().writeValue(response.outputStream, tokens)
    }

    @PostMapping(value = ["signUp"], consumes = ["application/json"], produces = ["application/json"])
    @ResponseBody
    fun signUp(@Valid @RequestBody newUser: User, response: HttpServletResponse, result: BindingResult): User {
        try {
            newUser.password = passwordEncoder.encode(newUser.password)
            userService.saveUser(newUser)
        }
        catch (e: Exception) {
            ObjectMapper().writeValue(response.outputStream, result.getFieldError("username")?.defaultMessage)
        }

        return newUser
    }


    @PutMapping("/addUserImage", consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
    fun addUserImage(username: String, image: MultipartFile): ByteArray? {
        //val user = userService.getUserById(id.toInt())
        val user = userService.getUserByUsername(username)
        user.image = image.bytes
        userService.saveUser(user)
        return user.image
    }

    @GetMapping("user/image")
    fun findUserImage(@RequestParam username: String): ByteArray? {
        val user = userService.getUserByUsername(username)
        return user.image
    }

}
