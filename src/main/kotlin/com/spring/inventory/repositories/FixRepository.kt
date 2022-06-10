package com.spring.inventory.repositories

import com.spring.inventory.entities.Fix
import com.spring.inventory.entities.Role
import org.springframework.data.jpa.repository.JpaRepository

interface FixRepository: JpaRepository<Fix, Int> {}
