package com.spring.inventory.config

import com.spring.inventory.jwt.*
import com.spring.inventory.services.CustomUserDetailsService
import com.spring.inventory.services.UserService
import org.modelmapper.ModelMapper
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
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource


@Configuration
@EnableWebSecurity
class WebSecurityConfig:  WebSecurityConfigurerAdapter() {
    @Autowired
    lateinit var jwtSecret: JwtSecret

    @Autowired
    lateinit var jwtConfig: JwtConfig

    @Autowired
    lateinit var userDetailsService: CustomUserDetailsService

    @Autowired
    lateinit var jwtUtil: JwtUtil

    @Autowired
    lateinit var userService: UserService

    override fun configure(http: HttpSecurity?) {
        val jwtAuthenticationFilter = JwtAuthenticationFilter(authenticationManagerBean(), jwtSecret.encryptSecret(), jwtUtil, userService)
        jwtAuthenticationFilter.setFilterProcessesUrl("/api/v1/public/signIn")
        http
            ?.csrf()
            ?.disable()
            ?.sessionManagement()
            ?.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            ?.and()
            ?.userDetailsService(userDetailsServiceBean())
            ?.authorizeRequests()
            ?.antMatchers( "/", "/api/v1/public/**", "/api/v1/user/**")
            ?.permitAll()
            ?.antMatchers("/api/v1/reader/**")
            ?.hasAnyAuthority("READER")
            ?.antMatchers("/api/v1/moderator/**")
            ?.hasAnyAuthority("READER", "MODERATOR")
            ?.antMatchers("/api/v1/admin/**")
            ?.hasAuthority("ADMIN")
            ?.anyRequest()
            ?.authenticated()
            ?.and()
            ?.addFilterBefore(CustomCorsFilter(), jwtAuthenticationFilter::class.java)
            ?.addFilter(jwtAuthenticationFilter)
            ?.addFilterAfter(JwtValidator(jwtSecret.encryptSecret(), jwtConfig, userDetailsService, jwtUtil), jwtAuthenticationFilter::class.java)
            //?.cors()
    }

    @Bean
    fun corsConfigurationSource(): CorsConfigurationSource {
        val corsConfig = CorsConfiguration()
        corsConfig.allowedOrigins = listOf("http://localhost:3000")
        corsConfig.allowedMethods = listOf("HEAD", "GET", "POST", "PUT", "DELETE", "PATCH")
        corsConfig.allowCredentials = true
        corsConfig.allowedHeaders = listOf("Authorization", "Cache-Control", "Content-Type");
        val source = UrlBasedCorsConfigurationSource()
        source.registerCorsConfiguration("/**", corsConfig)
        return source
    }

    @Bean
    fun getMapper(): ModelMapper? {
        val modelMapper = ModelMapper()
        modelMapper.getConfiguration().setSkipNullEnabled(true)
        return modelMapper
    }

    @Bean
    override fun authenticationManagerBean(): AuthenticationManager {
        return super.authenticationManagerBean()
    }

    override fun userDetailsServiceBean(): UserDetailsService {
        return super.userDetailsServiceBean()
    }

    override fun configure(auth: AuthenticationManagerBuilder) {
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
