package com.spring.inventory.repositories

import com.spring.inventory.entities.Classroom
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ClassroomRepository: JpaRepository<Classroom, Int> {
}
