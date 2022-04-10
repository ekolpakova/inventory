package com.spring.inventory.services

import com.fasterxml.jackson.databind.ObjectMapper
import com.spring.inventory.entities.Role
import com.spring.inventory.repositories.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import javax.servlet.http.HttpServletResponse

@Service
class CustomUserDetailsService: UserDetailsService {
    @Autowired
    lateinit var userRepository: UserRepository

    @Autowired
    lateinit var response: HttpServletResponse

    override fun loadUserByUsername(username: String): UserDetails? {
        var result: UserDetails? = null
        try {
            val user = userRepository.findByUsername(username)
            result = User(user.username, user.password, getAuthorities(user.roles!!)) //pass authorities as a 3rd parameter
        }
        catch (e: UsernameNotFoundException) {
            response.status = HttpStatus.UNAUTHORIZED.value()
            val data: HashMap<String, String> = HashMap()
            data["message"] = "Пользователь с именем $username не найдён"
            ObjectMapper().writeValue(response.outputStream, data)
        }
        return result
    }

    fun getAuthorities(roles: List<Role>): MutableSet<GrantedAuthority> {
        val authorities = mutableSetOf<GrantedAuthority>()
        for (role in roles) {
            authorities.add(SimpleGrantedAuthority(role.name))
            role.permissions?.stream()
                ?.map { p -> SimpleGrantedAuthority(p.name) }
                ?.forEach(authorities::add)
        }
        return authorities
    }
    //function to get authorities
}
