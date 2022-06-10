package com.spring.inventory.controllers

import com.fasterxml.jackson.databind.ObjectMapper
import com.spring.inventory.entities.Role
import com.spring.inventory.entities.User
import com.spring.inventory.payload.SignUpRequest
import com.spring.inventory.services.RoleService
import com.spring.inventory.services.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Controller
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import java.util.*
import javax.servlet.http.HttpServletResponse
import javax.validation.Valid

@RestController
@RequestMapping("/api/v1/admin")
@CrossOrigin(origins = ["http://localhost:8080", "http://localhost:3000"])
class UserController {
    @Autowired
    lateinit var userService: UserService

    @Autowired
    lateinit var roleService: RoleService

    @Autowired
    lateinit var passwordEncoder: PasswordEncoder

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

    /*@PutMapping("/addRoleToUser")
    fun addRoleToUser(@RequestParam username: String, @RequestParam name: String): MutableList<Role>? {
        val user = userService.getUserByUsername(username)
        val role = roleService.getRoleByName(name)
        user.roles?.add(role)
        userService.saveUser(user)
        return user.roles
    }*/


    @GetMapping("user/image")
    fun findUserImage(@RequestParam username: String): String? {
        val user = userService.getUserByUsername(username)
        return user.image
    }


}
