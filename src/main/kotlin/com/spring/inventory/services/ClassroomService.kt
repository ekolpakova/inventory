package com.spring.inventory.services

import com.spring.inventory.entities.Classroom
import com.spring.inventory.entities.InventoryItem
import com.spring.inventory.repositories.ClassroomRepository
import org.hibernate.mapping.Join
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import javax.persistence.criteria.CriteriaUpdate
import javax.persistence.criteria.Root

@Service
class ClassroomService {
    @Autowired
    lateinit var classroomRepository: ClassroomRepository

    fun getClassrooms(): MutableList<Classroom> {
        return classroomRepository.findAll()
    }
}
