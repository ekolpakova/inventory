package com.spring.inventory.controllers

import com.spring.inventory.entities.Role
import com.spring.inventory.entities.User
import com.spring.inventory.services.RoleService
import com.spring.inventory.services.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/admin")
@CrossOrigin(origins = ["http://localhost:8080", "http://localhost:3000"])
class AdminController {
    @Autowired
    lateinit var userService: UserService

    @Autowired
    lateinit var roleService: RoleService

    @GetMapping("/users")
    fun findAllUsers(): List<User> {
        return userService.getUsers()
    }

    @PutMapping("/addRoleToUser")
    fun addRoleToUser(@RequestParam username: String, @RequestParam name: String): MutableList<Role>? {
        val user = userService.getUserByUsername(username)
        val role = roleService.getRoleByName(name)
        if (user.roles?.contains(role) == false) {
            user.roles?.add(role)
            userService.saveUser(user)
        }
        return user.roles
    }

    @DeleteMapping("/removeRolesFromUser")
    fun removeRolesFromUser(@RequestParam username: String): MutableList<Role>? {
        val user = userService.getUserByUsername(username)
        user.roles?.clear()
        userService.saveUser(user)
        return user.roles
    }
}
