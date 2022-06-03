package com.spring.inventory.controllers

import com.spring.inventory.entities.Classroom
import com.spring.inventory.services.ClassroomService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@Transactional
@CrossOrigin(origins = ["http://localhost:8080", "http://localhost:3000"])
@RequestMapping("/api/v1/moderator")
class ClassroomController {
    @Autowired
    lateinit var classroomService: ClassroomService

    @GetMapping("classrooms")
    fun getClassrooms(): MutableList<Classroom> {
        return classroomService.getClassrooms()
    }
}
