package com.spring.inventory.controllers

import com.spring.inventory.entities.Category
import com.spring.inventory.entities.User
import com.spring.inventory.services.CategoryService
import com.spring.inventory.services.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@Transactional
@RequestMapping("/api/v1/moderator")
@CrossOrigin(origins = ["http://localhost:8080", "http://localhost:3000"])
class CategoryController {
    @Autowired
    lateinit var categoryService: CategoryService

    @GetMapping("/categories")
    fun findAllCategories(): List<Category> {
        return categoryService.getCategories()
    }
}
