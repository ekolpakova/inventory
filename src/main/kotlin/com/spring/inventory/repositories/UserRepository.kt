package com.spring.inventory.repositories

import com.spring.inventory.entities.Role
import com.spring.inventory.entities.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
// Kotlin distinguishes between mutable and immutable collections
interface UserRepository: JpaRepository<User, Int> {
    fun findByUsername(username: String): User
    /*fun addRoleToUser(user: User, role: Role) {
        val roles: MutableList<Role> = user.roles as MutableList<Role>
        roles.add(role)
    }*/
}
