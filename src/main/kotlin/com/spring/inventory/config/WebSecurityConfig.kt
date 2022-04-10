package com.spring.inventory.config

import com.spring.inventory.jwt.*
import com.spring.inventory.services.CustomUserDetailsService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.dao.DaoAuthenticationProvider
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Configuration
@EnableWebSecurity
class WebSecurityConfig:  WebSecurityConfigurerAdapter(){
    @Autowired
    lateinit var jwtSecret: JwtSecret

    @Autowired
    lateinit var jwtConfig: JwtConfig

    @Autowired
    lateinit var userDetailsService: CustomUserDetailsService

    @Autowired
    lateinit var jwtUtil: JwtUtil

    override fun configure(http: HttpSecurity?) {
        val jwtAuthenticationFilter = JwtAuthenticationFilter(authenticationManagerBean(), jwtSecret.encryptSecret(), jwtUtil)
        jwtAuthenticationFilter.setFilterProcessesUrl("/api/v1/public")
        http
            ?.csrf()
            ?.disable()
            ?.sessionManagement()
            ?.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            ?.and()
            ?.userDetailsService(userDetailsServiceBean())
            ?.authorizeRequests()
            ?.antMatchers( "/api/v1/public/**")
            ?.permitAll()
            ?.antMatchers("/api/v1/reader/**")
            ?.hasAuthority("READER")
            ?.antMatchers("/api/v1/moderator/**")
            ?.hasAuthority("MODERATOR")
            ?.anyRequest()
            ?.authenticated()
            ?.and()
            ?.addFilter(jwtAuthenticationFilter)
            ?.addFilterAfter(JwtValidator(jwtSecret.encryptSecret(), jwtConfig, userDetailsService), jwtAuthenticationFilter::class.java)
    }

    @Bean
    override fun authenticationManagerBean(): AuthenticationManager {
        return super.authenticationManagerBean()
    }

    override fun userDetailsServiceBean(): UserDetailsService {
        return super.userDetailsServiceBean()
    }

    override fun configure(auth: AuthenticationManagerBuilder) {
        //auth.authenticationProvider(daoAuthenticationProvider())
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder())
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder(10)
    }

    @Bean
    fun daoAuthenticationProvider(): DaoAuthenticationProvider {
        val provider = DaoAuthenticationProvider()
        provider.setPasswordEncoder(passwordEncoder())
        provider.setUserDetailsService(userDetailsServiceBean())
        return provider
    }

}
