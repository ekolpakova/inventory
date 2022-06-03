package com.spring.inventory.services

import com.spring.inventory.entities.Category
import com.spring.inventory.repositories.CategoryRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class CategoryService {
    @Autowired
    lateinit var categoryRepository: CategoryRepository

    fun getCategories(): List<Category> {
        return categoryRepository.findAll()
    }
}
