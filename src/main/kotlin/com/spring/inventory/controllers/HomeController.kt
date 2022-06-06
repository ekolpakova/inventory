package com.spring.inventory.controllers

import org.springframework.web.bind.annotation.GetMapping

class HomeController {
    @GetMapping("/")
    fun hello(): String {
        return "Hello World"
    }
}
