package com.spring.inventory.controllers

import com.spring.inventory.entities.Role
import com.spring.inventory.services.RoleService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@RestController
class RoleController {
    @Autowired
    lateinit var roleService: RoleService

    @GetMapping("/roleById/{id}")
    fun findRoleById(@PathVariable id: Int): Role {
        return roleService.getRoleById(id)
    }

    @GetMapping("/roleByName/{name}")
    fun findRoleByName(@PathVariable name: String): Role {
        return roleService.getRoleByName(name)
    }
}
