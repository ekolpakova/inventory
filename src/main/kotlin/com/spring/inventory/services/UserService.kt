package com.spring.inventory.services

import com.spring.inventory.entities.User
import com.spring.inventory.repositories.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*

@Service
class UserService {
    @Autowired
    lateinit var userRepository: UserRepository

    fun getUsers(): List<User> {
        return userRepository.findAll()
    }

    fun getUserById(id: Int): Optional<User> {
        return userRepository.findById(id)
    }

    fun getUserByUsername(username: String): User {
        return userRepository.findByUsername(username)
    }

    fun saveUser(user: User): User {
        return userRepository.save(user)
    }
}
