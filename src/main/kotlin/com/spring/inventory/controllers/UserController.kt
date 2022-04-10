package com.spring.inventory.controllers

import com.spring.inventory.entities.User
import com.spring.inventory.services.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
@RequestMapping("/api/v1/reader")
class UserController {
    @Autowired
    lateinit var userService: UserService

    //@PreAuthorize("hasAuthority('USERS:READ')")
    @GetMapping("/users")
    fun findAllUsers(): List<User> {
        return userService.getUsers()
    }

    @PreAuthorize("hasAuthority('USERS:WRITE')")
    @GetMapping("/userById/{id}")
    fun findUserById(@PathVariable id: Int): Optional<User> {
        return userService.getUserById(id)
    }
}
