package com.spring.inventory.repositories

import com.spring.inventory.entities.Test
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface TestRepository: JpaRepository<Test, Int> {
}
