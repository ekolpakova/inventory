package com.spring.inventory.controllers

import com.spring.inventory.entities.Test
import com.spring.inventory.repositories.TestRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("api/v1/public")
class TestController {
    @Autowired
    lateinit var testRepo: TestRepository;

    @GetMapping("/tests")
    fun tests(): MutableList<Test> {
        return testRepo.findAll()
    }
}
