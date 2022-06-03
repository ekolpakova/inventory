package com.spring.inventory.services

import com.spring.inventory.entities.Role
import com.spring.inventory.repositories.RoleRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class RoleService {
    @Autowired
    lateinit var roleRepository: RoleRepository

    fun saveRole(role: Role): Role {
        return roleRepository.save(role)
    }

    fun getRoleById(id: Int): Role {
        return roleRepository.getById(id)
    }

    fun getRoleByName(name: String): Role {
        return roleRepository.getRoleByName(name)
    }

}
