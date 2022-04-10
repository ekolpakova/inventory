package com.spring.inventory.jwt

import com.fasterxml.jackson.databind.ObjectMapper
import com.spring.inventory.services.CustomUserDetailsService
import io.jsonwebtoken.JwtException
import io.jsonwebtoken.Jwts
import org.springframework.http.HttpStatus
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.filter.OncePerRequestFilter
import java.util.stream.Collectors
import javax.crypto.SecretKey
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class JwtValidator(private val jwtSecret: SecretKey, jwtConfig: JwtConfig, userDetailsService: CustomUserDetailsService): OncePerRequestFilter() {
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain,
    ) {
        if (request.servletPath.equals("/api/v1/public")){
            filterChain.doFilter(request, response)
        }
        else {
            val authenticationHeader = request.getHeader("Authorization")
            if (authenticationHeader != null && authenticationHeader.startsWith("Bearer ")) {
                try {
                    val token = authenticationHeader.replace("Bearer ", "")
                    //jwt verification

                    //retrieving permissions here
                    val claims = Jwts
                        .parserBuilder()
                        .setSigningKey(jwtSecret.encoded)
                        .build()
                        .parseClaimsJws(token)
                        //.body
                        //.subject

                    val body = claims.body
                    val username = body.subject

                    val authorities = body["authorities"] as List<Map<String, String>>

                    val simpleGrantedAuthorities = authorities.stream()
                        .map { a -> SimpleGrantedAuthority(a["authority"]) }
                        .collect(Collectors.toSet())

                    val authReq = UsernamePasswordAuthenticationToken(
                        username,
                        null,
                        simpleGrantedAuthorities
                    )
                    SecurityContextHolder.getContext().authentication = authReq
                    filterChain.doFilter(request, response)
                }
                catch (e: JwtException) {
                    response.status = HttpStatus.UNAUTHORIZED.value()
                    val data: HashMap<String, String> = HashMap()
                    data["message"] = "Сессия истекла. Зайдите снова"
                    ObjectMapper().writeValue(response.outputStream, data)
                }
            }
            else {
                filterChain.doFilter(request, response)
            }
        }
        //filterChain.doFilter(request, response)
    }
}
