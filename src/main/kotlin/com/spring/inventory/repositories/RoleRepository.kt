package com.spring.inventory.repositories

import com.spring.inventory.entities.Role
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface RoleRepository: JpaRepository<Role, Int> {
    fun findByName(name: String): Role
}
